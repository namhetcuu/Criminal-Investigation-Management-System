package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.ForensicInvestResultDTO;
import com.Evidence_Service.event.caller.ForensicInvestResultCreatedEvent;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.kafka.KafkaEventPublisher;
import com.Evidence_Service.mapper.ForensicInvestResultMapper;
import com.Evidence_Service.entity.ForensicInvestResult;
import com.Evidence_Service.repository.ForensicInvestResultRepository;
import com.Evidence_Service.service.EvidenceService;
import com.Evidence_Service.service.ForensicInvestResultService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForensicInvestResultServiceImpl implements ForensicInvestResultService {

    private static final Logger log = LoggerFactory.getLogger(ForensicInvestResultServiceImpl.class);
    private final ForensicInvestResultRepository forensicInvestResultRepository;
    private final KafkaEventPublisher publisher;

    @CacheEvict(value = {"forensicInvestResult", "forensicInvestResultsByEvidence", "forensicInvestResultsByInvestigation"}, allEntries = true)
    @Override
    @Transactional
    public ForensicInvestResultDTO addForensicInvestResult(String evidenceId, ForensicInvestResultDTO dto) {
        try {
            log.info("Adding forensic investigation result for evidence ID: {}", evidenceId);
            // Check if evidence exists
            if (!forensicInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId)) {
                log.warn("Evidence not found for ID: {}", evidenceId);
                throw new AppException(ErrorCode.FORENSIC_INVEST_RESULT_NOT_FOUND);
            }

            // Convert DTO to entity and set evidence ID
            ForensicInvestResult result = ForensicInvestResultMapper.toEntity(dto);
            result.setEvidenceId(evidenceId);

            // Save to repository and publish event
            ForensicInvestResult saved = forensicInvestResultRepository.save(result);
            ForensicInvestResultDTO resultDTO = ForensicInvestResultMapper.toDTO(saved);
            publisher.send("digital-invest-result.created", ForensicInvestResultCreatedEvent.from(resultDTO));
            return resultDTO;
        } catch (AppException ae) {
            log.warn("Application exception during forensic result addition: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to add forensic investigation result for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"forensicInvestResult", "forensicInvestResultsByEvidence", "forensicInvestResultsByInvestigation"}, allEntries = true)
    @Override
    @Transactional
    public void assignForensicInvestResult(ResultInvestAssignedEvent event) {
        try {
            List<ForensicInvestResult> forensicInvestResults = forensicInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(event.getEvidenceId());

            if (forensicInvestResults ==  null) {
                ForensicInvestResult.builder()
                        .evidenceId(event.getEvidenceId())
                        .result(event.getContent())
                        .uploadFile(event.getEvidenceId())
                        .build();
            } else {
                for (ForensicInvestResult forensicInvestResult : forensicInvestResults) {
                    forensicInvestResult.setEvidenceId(event.getEvidenceId());
                    forensicInvestResult.setResult(event.getContent());
                    forensicInvestResult.setUploadFile(event.getUploadFile());
                    forensicInvestResultRepository.save(forensicInvestResult);
                }
            }
            log.info("Assigned Investigation");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "forensicInvestResult", key = "#resultId")
    @Override
    public ForensicInvestResultDTO getForensicInvestById(String resultId) {
        try {
            log.info("Retrieving forensic investigation result with ID: {}", resultId);
            return forensicInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                    .map(ForensicInvestResultMapper::toDTO)
                    .orElseThrow(() -> {
                        log.warn("Forensic investigation result not found with ID: {}", resultId);
                        return new AppException(ErrorCode.FORENSIC_INVEST_RESULT_NOT_FOUND);
                    });
        } catch (AppException ae) {
            log.warn("Application exception during forensic result retrieval: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to retrieve forensic investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "forensicInvestResultsByEvidence", key = "#evidenceId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<ForensicInvestResultDTO> getAllForensicInvestByEvidenceId(String evidenceId, Pageable pageable) {
        try {
            return forensicInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId, pageable)
                    .map(ForensicInvestResultMapper::toDTO);
        } catch (Exception ex) {
            log.error("Failed to retrieve forensic investigation results for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"forensicInvestResult", "forensicInvestResultsByEvidence", "forensicInvestResultsByInvestigation"}, allEntries = true)
    @Override
    @Transactional
    public ForensicInvestResultDTO updateForensicInvest(String evidenceId, String resultId, ForensicInvestResultDTO dto) {
        try {
            log.info("Updating forensic investigation result with ID: {} for evidence ID: {}", resultId, evidenceId);
            // Find existing result
            forensicInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.FORENSIC_INVEST_RESULT_NOT_FOUND));

            ForensicInvestResult entity;
            // Update entity with new data
            entity = ForensicInvestResultMapper.toEntity(dto);
            entity.setEvidenceId(evidenceId);
            return ForensicInvestResultMapper.toDTO(forensicInvestResultRepository.save(entity));
        } catch (AppException ae) {
            log.warn("Application exception during forensic result update: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to update forensic investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"forensicInvestResult", "forensicInvestResultsByEvidence", "forensicInvestResultsByInvestigation"}, allEntries = true)
    @Override
    @Transactional
    public void deleteForensicInvestByResultId(String resultId) {
        try {
            log.info("Deleting forensic investigation result with ID: {}", resultId);
            // Find existing result
            ForensicInvestResult forensicInvestResult = forensicInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.FORENSIC_INVEST_RESULT_NOT_FOUND));

            forensicInvestResult.setDeleted(true);
            forensicInvestResultRepository.save(forensicInvestResult);
        } catch (AppException ae) {
            log.warn("Application exception during forensic result deletion: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to delete forensic investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            return forensicInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"forensicInvestResult", "forensicInvestResultsByEvidence", "forensicInvestResultsByInvestigation"}, allEntries = true)
    @Override
    @Transactional
    public void deleteByEvidenceId(String evidenceId) {
        try {
            List<ForensicInvestResult> forensicInvestResults = forensicInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId);

            if (forensicInvestResults == null) throw new AppException(ErrorCode.FORENSIC_INVEST_RESULT_NOT_FOUND);

            for (ForensicInvestResult forensicInvestResult : forensicInvestResults) {
                forensicInvestResult.setDeleted(true);
                forensicInvestResultRepository.save(forensicInvestResult);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}