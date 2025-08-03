package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.PhysicalInvestResultDTO;
import com.Evidence_Service.event.caller.PhysicalInvestResultCreatedEvent;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.kafka.KafkaEventPublisher;
import com.Evidence_Service.mapper.PhysicalInvestResultMapper;
import com.Evidence_Service.entity.PhysicalInvestResult;
import com.Evidence_Service.repository.PhysicalInvestResultRepository;
import com.Evidence_Service.service.EvidenceService;
import com.Evidence_Service.service.PhysicalInvestResultService;
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
public class PhysicalInvestResultServiceImpl implements PhysicalInvestResultService {

    private static final Logger log = LoggerFactory.getLogger(PhysicalInvestResultServiceImpl.class);

    private final PhysicalInvestResultRepository physicalInvestResultRepository;
    private final KafkaEventPublisher publisher;

    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public PhysicalInvestResultDTO addPhysicalInvestResult(String evidenceId, PhysicalInvestResultDTO dto) {
        try {
            // Convert DTO to entity and set evidence ID
            PhysicalInvestResult result = PhysicalInvestResultMapper.toEntity(dto);
            result.setEvidenceId(evidenceId);

            // Save to repository and publish event
            PhysicalInvestResult saved = physicalInvestResultRepository.save(result);
            PhysicalInvestResultDTO resultDTO = PhysicalInvestResultMapper.toDTO(saved);
            publisher.send("digital-invest-result.created", PhysicalInvestResultCreatedEvent.from(resultDTO));
            return resultDTO;
        } catch (AppException ae) {
            log.error("Application exception during physical result addition: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to add physical investigation result for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "physicalInvestResult", key = "#resultId")
    @Override
    public PhysicalInvestResultDTO getPhysicalInvestByResultId(String resultId) {
        try {
            return physicalInvestResultRepository.findById(resultId)
                    .map(PhysicalInvestResultMapper::toDTO)
                    .orElseThrow(() -> new AppException(ErrorCode.PHYSICAL_INVEST_RESULT_NOT_FOUND));
        } catch (AppException ae) {
            log.error("Application exception during physical result retrieval: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to retrieve physical investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "physicalInvestResultsByEvidence", key = "#evidenceId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<PhysicalInvestResultDTO> getAllPhysicalInvestByEvidenceId(String evidenceId, Pageable pageable) {
        try {
            return physicalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId, pageable)
                    .map(PhysicalInvestResultMapper::toDTO);
        } catch (Exception ex) {
            log.error("Failed to retrieve physical investigation results for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "physicalInvestResultsByInvestigation", key = "#investigationId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<PhysicalInvestResultDTO> getAllPhysicalInvestByInvestigationId(String investigationId, Pageable pageable) {
        try {
            // Note: Original implementation returns null, consider implementing proper logic
            log.warn("getAllPhysicalInvestByInvestigationId not implemented for investigation ID: {}", investigationId);
            return null;
        } catch (Exception ex) {
            log.error("Failed to retrieve physical investigation results for investigation ID {}: {}", investigationId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public PhysicalInvestResultDTO updatePhysicalInvest(String evidenceId, String resultId, PhysicalInvestResultDTO dto) {
        try {
            // Find existing result
            physicalInvestResultRepository.findById(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.PHYSICAL_INVEST_RESULT_NOT_FOUND));

            PhysicalInvestResult entity;
            // Update entity with new data
            entity = PhysicalInvestResultMapper.toEntity(dto);
            entity.setEvidenceId(evidenceId);
            return PhysicalInvestResultMapper.toDTO(physicalInvestResultRepository.save(entity));
        } catch (AppException ae) {
            log.error("Application exception during physical result update: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to update physical investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deletePhysicalInvestByResultId(String resultId) {
        try {
            // Find existing result
            PhysicalInvestResult physicalInvestResult = physicalInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId);
            if (physicalInvestResult == null) {
                throw new AppException(ErrorCode.PHYSICAL_INVEST_RESULT_NOT_FOUND);
            }
            physicalInvestResult.setDeleted(true);
            physicalInvestResultRepository.save(physicalInvestResult);
        } catch (AppException ae) {
            log.error("Application exception during physical result deletion: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to delete physical investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            return physicalInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deleteByEvidenceId(String evidenceId) {
        try {
            List<PhysicalInvestResult> physicalInvestResults = physicalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId);

            if (physicalInvestResults == null) throw new AppException(ErrorCode.PHYSICAL_INVEST_RESULT_NOT_FOUND);

            for (PhysicalInvestResult physicalInvestResult : physicalInvestResults) {
                physicalInvestResult.setDeleted(true);
                physicalInvestResultRepository.save(physicalInvestResult);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"physicalInvestResult", "physicalInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void assignPhysicalInvestResult(ResultInvestAssignedEvent event) {
        try {
            List<PhysicalInvestResult> physicalInvestResults = physicalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(event.getEvidenceId());

            if (physicalInvestResults ==  null) {
                PhysicalInvestResult.builder()
                        .evidenceId(event.getEvidenceId())
                        .result(event.getContent())
                        .uploadFile(event.getUploadFile())
                        .build();
            } else {
                for (PhysicalInvestResult physicalInvestResult : physicalInvestResults) {
                    physicalInvestResult.setEvidenceId(event.getEvidenceId());
                    physicalInvestResult.setResult(event.getContent());
                    physicalInvestResult.setUploadFile(event.getUploadFile());
                    physicalInvestResultRepository.save(physicalInvestResult);
                }
            }
            log.info("Assigned Investigation");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}