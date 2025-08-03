package com.Evidence_Service.repository;

import com.Evidence_Service.entity.RecordInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordInfoRepository extends JpaRepository<RecordInfo, String> {
    Page<RecordInfo> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId, Pageable pageable);

    List<RecordInfo> findAllByEvidenceIdAndIsDeletedFalse(String evidenceId);

    Page<RecordInfo> findAllByIsDeletedFalse(Pageable pageable);
    Optional<RecordInfo> findByRecordInfoIdAndIsDeletedFalse(String recordInfoId);
    boolean existsByRecordInfoIdAndIsDeletedFalse(String recordInfoId);

    boolean existsByEvidenceIdAndIsDeletedFalse(String evidenceId);

}
