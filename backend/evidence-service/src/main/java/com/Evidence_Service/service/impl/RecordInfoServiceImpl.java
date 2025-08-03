package com.Evidence_Service.service.impl;

import com.Evidence_Service.dto.RecordInfoDTO;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.mapper.RecordInfoMapper;
import com.Evidence_Service.entity.RecordInfo;
import com.Evidence_Service.repository.RecordInfoRepository;
import com.Evidence_Service.service.RecordInfoService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordInfoServiceImpl implements RecordInfoService {

    private static final Logger log = LoggerFactory.getLogger(RecordInfoServiceImpl.class);

    private final RecordInfoRepository recordInfoRepository;
    private final RecordInfoMapper recordInfoMapper;
    private final Validator validator;

    @CacheEvict(value = {"recordInfo", "recordInfoByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public RecordInfoDTO createRecordInfo(RecordInfoDTO dto) {
        // Fallback validation
        Set<jakarta.validation.ConstraintViolation<RecordInfoDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            log.error("Validation failed for RecordInfoDTO: {}", violations);
            throw new AppException(ErrorCode.INVALID_KEY, "Invalid RecordInfoDTO: " + violations);
        }

        try {
            // Convert DTO to entity
            RecordInfo entity = recordInfoMapper.toEntity(dto);
            if (entity == null) {
                log.error("Failed to map DTO to entity: {}", dto);
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Failed to map RecordInfoDTO to entity");
            }
            // Generate unique ID
            entity.setRecordInfoId(UUID.randomUUID().toString());
            // Set timestamps if not provided
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(LocalDateTime.now());
            }
            if (entity.getUpdatedAt() == null) {
                entity.setUpdatedAt(LocalDateTime.now());
            }
            // Save entity
            RecordInfo savedEntity = recordInfoRepository.save(entity);
            // Map to DTO
            RecordInfoDTO result = recordInfoMapper.toDTO(savedEntity);
            if (result == null) {
                log.error("Failed to map saved entity to DTO: {}", savedEntity);
                throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Failed to map saved entity to RecordInfoDTO");
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to create record info: {}", ex.getMessage(), ex);
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION, "Failed to create record info: " + ex.getMessage());
        }
    }

    @Cacheable(value = "recordInfoByAll", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<RecordInfoDTO> getAllRecordInfo(Pageable pageable) {
        try {
            return recordInfoRepository.findAllByIsDeletedFalse(pageable)
                    .map(recordInfoMapper::toDTO);
        } catch (Exception ex) {
            log.error("Failed to retrieve all record info: {}", ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "recordInfo", key = "#recordInfoId")
    @Override
    public RecordInfoDTO getRecordInfoByRecordInfoId(String recordInfoId) {
        try {
            return recordInfoRepository.findByRecordInfoIdAndIsDeletedFalse(recordInfoId)
                    .map(recordInfoMapper::toDTO)
                    .orElseThrow(() -> new AppException(ErrorCode.RECORD_INFO_NOT_FOUND));
        } catch (AppException ae) {
            log.error("Application exception during record info retrieval: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to retrieve record info with ID {}: {}", recordInfoId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Cacheable(value = "recordInfoByEvidence", key = "#evidenceId + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    @Override
    public Page<RecordInfoDTO> getRecordInfoByEvidenceId(String evidenceId, Pageable pageable) {
        try {
            return recordInfoRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId, pageable)
                    .map(recordInfoMapper::toDTO);
        } catch (Exception ex) {
            log.error("Failed to retrieve record info for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"recordInfo", "recordInfoByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public RecordInfoDTO updateRecordInfo(String recordInfoId, RecordInfoDTO dto) {
        try {
            // Find existing record
            recordInfoRepository.findByRecordInfoIdAndIsDeletedFalse(recordInfoId)
                    .orElseThrow(() -> new AppException(ErrorCode.RECORD_INFO_NOT_FOUND));

            RecordInfo entity;
            // Update entity with new data
            entity = recordInfoMapper.toEntity(dto);
            return recordInfoMapper.toDTO(recordInfoRepository.save(entity));
        } catch (AppException ae) {
            log.error("Application exception during record info update: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to update record info with ID {}: {}", recordInfoId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"recordInfo", "recordInfoByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deleteRecordInfoByRecordInfoId(String recordInfoId) {
        try {
            // Check if record exists
            if (!recordInfoRepository.existsByRecordInfoIdAndIsDeletedFalse(recordInfoId)) {
                throw new AppException(ErrorCode.RECORD_INFO_NOT_FOUND);
            }
            recordInfoRepository.deleteById(recordInfoId);
        } catch (AppException ae) {
            log.error("Application exception during record info deletion: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to delete record info with ID {}: {}", recordInfoId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            return recordInfoRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(value = {"recordInfo", "recordInfoByEvidence"}, allEntries = true)
    @Override
    @Transactional
    public void deleteByEvidenceId(String evidenceId) {
        try {
            List<RecordInfo> recordInfo = recordInfoRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId);

            if (recordInfo == null) throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);

            for (RecordInfo record : recordInfo) {
                record.setDeleted(true);
                recordInfoRepository.save(record);
            }
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(ErrorCode.RECORD_INFO_NOT_FOUND);
        }
    }
}