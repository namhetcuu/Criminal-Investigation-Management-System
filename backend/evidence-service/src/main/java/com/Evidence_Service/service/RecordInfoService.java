package com.Evidence_Service.service;

import com.Evidence_Service.dto.RecordInfoDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordInfoService {

    RecordInfoDTO createRecordInfo(RecordInfoDTO dto);

    Page<RecordInfoDTO> getAllRecordInfo(Pageable pageable);


    RecordInfoDTO getRecordInfoByRecordInfoId(String recordInfoId);

    Page<RecordInfoDTO> getRecordInfoByEvidenceId(String evidenceId, Pageable pageable);

    RecordInfoDTO updateRecordInfo(String recordInfoId, RecordInfoDTO dto);

    void deleteRecordInfoByRecordInfoId(String recordInfoId);

    boolean existsByEvidenceId(String evidenceId);

    void deleteByEvidenceId(String evidenceId);
}
