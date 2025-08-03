package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.DigitalInvestResultDTO;
import com.Evidence_Service.event.caller.DigitalInvestResultCreatedEvent;
import com.Evidence_Service.event.listener.ResultInvestAssignedEvent;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.kafka.EventPublisher;
import com.Evidence_Service.mapper.DigitalInvestResultMapper;
import com.Evidence_Service.entity.DigitalInvestResult;
import com.Evidence_Service.repository.DigitalInvestResultRepository;
import com.Evidence_Service.service.DigitalInvestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DigitalInvestResultServiceImpl implements DigitalInvestResultService {

    private final DigitalInvestResultRepository digitalInvestResultRepository;
    private final EventPublisher publisher;

    @Override
    @Transactional
    @CacheEvict(value = {"digitalInvestResult", "digitalInvestList"}, allEntries = true)
    public DigitalInvestResultDTO addDigitalInvestResult(String evidenceId, DigitalInvestResultDTO dto) {
        try {
            if (digitalInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId)) {
                throw new AppException(ErrorCode.DIGITAL_INVEST_RESULT_ALREADY_EXISTS); // Adjusted error code
            }

            DigitalInvestResult result = DigitalInvestResultMapper.toEntity(dto);
            result.setEvidenceId(evidenceId);
            result = digitalInvestResultRepository.save(result);
            var resultDTO = DigitalInvestResultMapper.toDTO(result);
            publisher.send("digital-invest-result.created", DigitalInvestResultCreatedEvent.from(resultDTO));
            return resultDTO;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error adding digital investigation result for evidenceId: {}", evidenceId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to add digital investigation result");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"digitalInvestResult", "digitalInvestList"}, allEntries = true)
    public void assignDigitalInvestResult(ResultInvestAssignedEvent event) {
        try {
            List<DigitalInvestResult> digitalInvestResults = digitalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(event.getEvidenceId());
            if (digitalInvestResults.isEmpty()) { // Fixed null check
                DigitalInvestResult result = DigitalInvestResult.builder()
                        .evidenceId(event.getEvidenceId())
                        .result(event.getContent())
                        .uploadFile(event.getUploadFile())
                        .build();
                digitalInvestResultRepository.save(result);
                publisher.send("digital-invest-result.created", DigitalInvestResultCreatedEvent.from(DigitalInvestResultMapper.toDTO(result)));
            } else {
                for (DigitalInvestResult digitalInvestResult : digitalInvestResults) {
                    digitalInvestResult.setResult(event.getContent());
                    digitalInvestResult.setUploadFile(event.getUploadFile());
                    digitalInvestResultRepository.save(digitalInvestResult);
                }
            }
            log.info("Assigned digital investigation result for evidenceId: {}", event.getEvidenceId());
        } catch (Exception e) {
            log.error("Error assigning digital investigation result for evidenceId: {}", event.getEvidenceId(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "digitalInvestResult", key = "#resultId")
    public DigitalInvestResultDTO getDigitalInvestByResultId(String resultId) {
        try {
            return DigitalInvestResultMapper.toDTO(
                    digitalInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                            .orElseThrow(() -> new AppException(ErrorCode.DIGITAL_INVEST_RESULT_NOT_FOUND))
            );
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving digital investigation result by resultId: {}", resultId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to get digital investigation result");
        }
    }

    @Override
    @Cacheable(value = "digitalInvestList", key = "#evidenceId + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<DigitalInvestResultDTO> getAllDigitalInvestByEvidenceId(String evidenceId, Pageable pageable) {
        try {
            return digitalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId, pageable)
                    .map(DigitalInvestResultMapper::toDTO);
        } catch (Exception e) {
            log.error("Error listing digital investigation results for evidenceId: {}", evidenceId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to list digital investigation results");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"digitalInvestResult", "digitalInvestList"}, allEntries = true)
    public DigitalInvestResultDTO updateDigitalInvest(String evidenceId, String resultId, DigitalInvestResultDTO dto) {
        try {
            var existing = digitalInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.DIGITAL_INVEST_RESULT_NOT_FOUND));
            DigitalInvestResult updated = DigitalInvestResultMapper.toEntity(dto);
            updated.setResultId(existing.getResultId());
            updated.setEvidenceId(existing.getEvidenceId());
            updated = digitalInvestResultRepository.save(updated);
            var resultDTO = DigitalInvestResultMapper.toDTO(updated);
            return resultDTO;
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error updating digital investigation result with resultId: {}", resultId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to update digital investigation result");
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"digitalInvestResult", "digitalInvestList"}, allEntries = true)
    public void deleteDigitalInvestByResultId(String resultId) {
        try {
            DigitalInvestResult digitalInvestResult = digitalInvestResultRepository.findByResultIdAndIsDeletedFalse(resultId)
                    .orElseThrow(() -> new AppException(ErrorCode.DIGITAL_INVEST_RESULT_NOT_FOUND));
            digitalInvestResult.setDeleted(true);
            digitalInvestResultRepository.save(digitalInvestResult);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting digital investigation result with resultId: {}", resultId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to delete digital investigation result");
        }
    }

    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            return digitalInvestResultRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (Exception e) {
            log.error("Error checking existence for evidenceId: {}", evidenceId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"digitalInvestResult", "digitalInvestList"}, allEntries = true)
    public void deleteByEvidenceId(String evidenceId) {
        try {
            List<DigitalInvestResult> digitalInvestResults = digitalInvestResultRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId);
            if (digitalInvestResults.isEmpty()) {
                throw new AppException(ErrorCode.DIGITAL_INVEST_RESULT_NOT_FOUND);
            }
            for (DigitalInvestResult result : digitalInvestResults) {
                result.setDeleted(true);
                digitalInvestResultRepository.save(result);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting digital investigation results for evidenceId: {}", evidenceId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}