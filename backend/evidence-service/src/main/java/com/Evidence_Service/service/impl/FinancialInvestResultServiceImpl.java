package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.FinancialInvestResultDTO;
import com.Evidence_Service.event.caller.FinancialInvestResultCreatedEvent;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.kafka.KafkaEventPublisher;
import com.Evidence_Service.mapper.FinancialInvestResultMapper;
import com.Evidence_Service.entity.FinancialInvestResult;
import com.Evidence_Service.repository.FinancialInvestResultRepository;
import com.Evidence_Service.service.EvidenceService;
import com.Evidence_Service.service.FinancialInvestResultService;
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
public class FinancialInvestResultServiceImpl implements FinancialInvestResultService {

    private static final Logger log = LoggerFactory.getLogger(FinancialInvestResultServiceImpl.class);

    private final FinancialInvestResultRepository financialInvestResultRepository;
    private final KafkaEventPublisher publisher;

    @CacheEvict(value = {"financialInvestResult", "financialInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public FinancialInvestResultDTO addFinancialInvestResult(String evidenceId, FinancialInvestResultDTO dto) {
        try {
            log.info("Adding financial investigation result for evidence ID: {}", evidenceId);

            // Convert DTO to entity and set evidence ID
            FinancialInvestResult result = FinancialInvestResultMapper.toEntity(dto);
            result.setEvidenceId(evidenceId);

            // Save to repository and publish event
            FinancialInvestResult saved = financialInvestResultRepository.save(result);
            FinancialInvestResultDTO resultDTO = FinancialInvestResultMapper.toDTO(saved);
            publisher.send("digital-invest-result.created", FinancialInvestResultCreatedEvent.from(resultDTO));
            log.info("Successfully added financial investigation result with ID: {}", resultDTO.getResultId());
            return resultDTO;
        } catch (AppException ae) {
            log.warn("Application exception during financial result addition: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to add financial investigation result for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "financialInvestResult", key = "#resultId")
    @Override
    public FinancialInvestResultDTO getFinancialInvestById(String resultId) {
        try {
            log.info("Retrieving financial investigation result with ID: {}", resultId);
            FinancialInvestResultDTO result = financialInvestResultRepository.findById(resultId)
                    .map(FinancialInvestResultMapper::toDTO)
                    .orElseThrow(() -> {
                        log.warn("Financial investigation result not found with ID: {}", resultId);
                        return new AppException(ErrorCode.FINANCIAL_INVEST_RESULT_NOT_FOUND);
                    });
            log.info("Successfully retrieved financial investigation result with ID: {}", resultId);
            return result;
        } catch (AppException ae) {
            log.warn("Application exception during financial result retrieval: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to retrieve financial investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"financialInvestResult", "financialInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void assignFinancialInvestResult(ResultInvestAssignedEvent event) {
        try {
            List<FinancialInvestResult> financialInvestResults = financialInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(event.getEvidenceId());

            if (financialInvestResults ==  null) {
                FinancialInvestResult.builder()
                        .evidenceId(event.getEvidenceId())
                        .result(event.getContent())
                        .uploadFile(event.getUploadFile())
                        .build();
            } else {
                for (FinancialInvestResult financialInvestResult : financialInvestResults) {
                    financialInvestResult.setEvidenceId(event.getEvidenceId());
                    financialInvestResult.setResult(event.getContent());
                    financialInvestResult.setUploadFile(event.getUploadFile());
                    financialInvestResultRepository.save(financialInvestResult);
                }
            }
            log.info("Assigned Investigation");
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "financialInvestResultsByEvidence", key = "#evidenceId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<FinancialInvestResultDTO> getAllFinancialInvestByEvidenceId(String evidenceId, Pageable pageable) {
        try {
            log.info("Retrieving all financial investigation results for evidence ID: {}", evidenceId);
            Page<FinancialInvestResultDTO> results = financialInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId, pageable)
                    .map(FinancialInvestResultMapper::toDTO);
            log.info("Successfully retrieved {} financial investigation results for evidence ID: {}", results.getTotalElements(), evidenceId);
            return results;
        } catch (Exception ex) {
            log.error("Failed to retrieve financial investigation results for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"financialInvestResult", "financialInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public FinancialInvestResultDTO updateFinancialInvest(String evidenceId, String resultId, FinancialInvestResultDTO dto) {
        try {
            log.info("Updating financial investigation result with ID: {} for evidence ID: {}", resultId, evidenceId);

            financialInvestResultRepository.findById(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.FINANCIAL_INVEST_RESULT_NOT_FOUND));

            FinancialInvestResult entity;
            entity = FinancialInvestResultMapper.toEntity(dto);
            entity.setEvidenceId(evidenceId);
            FinancialInvestResultDTO result = FinancialInvestResultMapper.toDTO(financialInvestResultRepository.save(entity));
            log.info("Successfully updated financial investigation result with ID: {}", resultId);
            return result;
        } catch (AppException ae) {
            log.warn("Application exception during financial result update: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to update financial investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"financialInvestResult", "financialInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deleteFinancialInvestByResultId(String resultId) {
        try {
            log.info("Deleting financial investigation result with ID: {}", resultId);
            // Find existing result
            FinancialInvestResult financialInvestResult = financialInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId);
            if (financialInvestResult == null) {
                throw new AppException(ErrorCode.FINANCIAL_INVEST_RESULT_NOT_FOUND);
            }
            financialInvestResult.setDeleted(true);
            financialInvestResultRepository.save(financialInvestResult);
            log.info("Successfully deleted financial investigation result with ID: {}", resultId);
        } catch (AppException ae) {
            log.warn("Application exception during financial result deletion: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to delete financial investigation result with ID {}: {}", resultId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            return financialInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (AppException e) {
            throw  e;
        } catch (Exception e) {
            throw  new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"financialInvestResult", "financialInvestResultsByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deleteByEvidenceId(String evidenceId) {
        try {
            List<FinancialInvestResult> financialInvestResults = financialInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId);

            if (financialInvestResults == null) throw new AppException(ErrorCode.FINANCIAL_INVEST_RESULT_NOT_FOUND);

            for(FinancialInvestResult financialInvestResult : financialInvestResults) {
                financialInvestResult.setDeleted(true);
                financialInvestResultRepository.save(financialInvestResult);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}