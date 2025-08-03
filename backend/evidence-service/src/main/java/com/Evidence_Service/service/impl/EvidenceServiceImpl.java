package com.Evidence_Service.service.impl;

import com.Evidence_Service.client.*;
import com.Evidence_Service.dto.*;
import com.Evidence_Service.event.caller.*;
import com.Evidence_Service.event.listener.*;
import com.Evidence_Service.exception.AppException;
import com.Evidence_Service.exception.ErrorCode;
import com.Evidence_Service.kafka.EventPublisher;
import com.Evidence_Service.mapper.EvidenceMapper;
import com.Evidence_Service.entity.*;
import com.Evidence_Service.repository.*;
import com.Evidence_Service.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvidenceServiceImpl implements EvidenceService {

    private static final Logger log = LoggerFactory.getLogger(EvidenceServiceImpl.class);

    private final EvidenceRepository evidenceRepository;
    private final WarrantEvidenceRepository warrantEvidenceRepository;
    private final SuspectEvidenceRepository suspectEvidenceRepository;
    private final CaseEvidenceRepository caseEvidenceRepository;
    private final ReportEvidenceRepository reportEvidenceRepository;
    private final ReportClient reportClient;
    private final CaseClient caseClient;
    private final WarrantClient warrantClient;
    private final SuspectClient suspectClient;
    private final EventPublisher eventPublisher;
    private final DigitalInvestResultService digitalInvestResultService;
    private final FinancialInvestResultService financialInvestResultService;
    private final PhysicalInvestResultService physicalInvestResultService;
    private final ForensicInvestResultService forensicInvestResultService;
    private final RecordInfoService recordInfoService;

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public EvidenceDTO createEvidence(EvidenceDTO dto) {
        try {
            log.info("Creating evidence with data: {}", dto);
            Evidence entity = EvidenceMapper.toEntity(dto);
            Evidence saved = evidenceRepository.save(entity);
            eventPublisher.send("evidence.created", EvidenceMapper.toCreatedEvent(saved));
            log.info("Successfully created evidence with ID: {}", saved.getEvidenceId());
            return EvidenceMapper.toDTO(saved);
        } catch (Exception ex) {
            log.error("Failed to create evidence: {}", ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteByEvidenceId(String evidenceId) {
        try {
            log.info("Deleting evidence with ID: {}", evidenceId);
            Evidence evidence = getEvidenceOrThrow(evidenceId);
            evidence.setDeleted(true);
            evidence.setStatus(EvidenceStatus.DELETED);
            evidenceRepository.save(evidence);
            eventPublisher.send("evidence.deleted", new EvidenceDeletedEvent(evidenceId));
            log.info("Successfully deleted evidence with ID: {}", evidenceId);
        } catch (Exception ex) {
            log.error("Failed to delete evidence with ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteByInvestigationPlanId(String investigationPlanId) {
        try {
            List<Evidence> evidences = evidenceRepository.findAllByInvestigationPlanIdAndIsDeletedFalse(investigationPlanId);
            if (evidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            for (Evidence evidence : evidences) {
                evidence.setDeleted(true);
                evidenceRepository.save(evidence);
            }
            log.info("Deleted evidences for investigationPlanId: {}", investigationPlanId);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error deleting evidences for investigationPlanId: {}", investigationPlanId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public EvidenceDTO assignCase(String evidenceId, CaseDTO dto) {
        try {
            log.info("Assigning case {} to evidence {}", dto.getCaseId(), evidenceId);
            Evidence evidence = getEvidenceOrThrow(evidenceId);
            evidence.setCaseId(dto.getCaseId());
            evidence.setStatus(EvidenceStatus.ASSIGNED);
            evidenceRepository.save(evidence);
            eventPublisher.send("case.assigned", new CaseAssignedEvent(evidenceId, dto.getCaseId(), LocalDateTime.now()));
            log.info("Successfully assigned case {} to evidence {}", dto.getCaseId(), evidenceId);
            return EvidenceMapper.toDTO(evidence);
        } catch (AppException ae) {
            log.warn("Application exception during case assignment: {}", ae.getErrorCode());
            throw ae;
        } catch (Exception ex) {
            log.error("Failed to assign case to evidence {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public EvidenceDTO assignSuspect(String evidenceId, SuspectDTO dto) {
        try {
            log.info("Assigning suspect {} to evidence {}", dto.getSuspectId(), evidenceId);
            Evidence evidence = getEvidenceOrThrow(evidenceId);
            SuspectEvidence se = new SuspectEvidence();
            se.setEvidenceId(evidenceId);
            se.setSuspectId(dto.getSuspectId());
            suspectEvidenceRepository.save(se);
            eventPublisher.send("suspect.assigned", new SuspectAssignedEvent(evidenceId, dto.getSuspectId(), LocalDateTime.now()));
            log.info("Successfully assigned suspect {} to evidence {}", dto.getSuspectId(), evidenceId);
            return EvidenceMapper.toDTO(evidence);
        } catch (Exception ex) {
            log.error("Failed to assign suspect to evidence {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public EvidenceDTO assignWarrant(String evidenceId, WarrantDTO dto) {
        try {
            log.info("Assigning warrant {} to evidence {}", dto.getWarrantId(), evidenceId);
            Evidence evidence = getEvidenceOrThrow(evidenceId);
            WarrantEvidence we = new WarrantEvidence();
            we.setEvidenceId(evidenceId);
            we.setWarrantId(dto.getWarrantId());
            warrantEvidenceRepository.save(we);
            eventPublisher.send("warrant.assigned", new WarrantAssignedEvent(evidenceId, dto.getWarrantId(), LocalDateTime.now()));
            log.info("Successfully assigned warrant {} to evidence {}", dto.getWarrantId(), evidenceId);
            return EvidenceMapper.toDTO(evidence);
        } catch (Exception ex) {
            log.error("Failed to assign warrant to evidence {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public EvidenceDTO updateEvidence(EvidenceDTO dto) {
        try {
            log.info("Updating evidence with ID: {}", dto.getEvidenceId());
            Evidence entity = getEvidenceOrThrow(dto.getEvidenceId());
            entity.setDescription(dto.getDescription());
            Evidence saved = evidenceRepository.save(entity);
            log.info("Successfully updated evidence with ID: {}", dto.getEvidenceId());
            return EvidenceMapper.toDTO(saved);
        } catch (Exception ex) {
            log.error("Failed to update evidence with ID {}: {}", dto.getEvidenceId(), ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "evidence", key = "#evidenceId")
    public EvidenceDTO getByEvidenceId(String evidenceId) {
        try {
            log.info("Retrieving evidence with ID: {}", evidenceId);
            EvidenceDTO result = EvidenceMapper.toDTO(getEvidenceOrThrow(evidenceId));
            log.info("Successfully retrieved evidence with ID: {}", evidenceId);
            return result;
        } catch (AppException ae) {
            log.warn("Evidence not found with ID: {}", evidenceId);
            throw ae;
        } catch (Exception ex) {
            log.error("Error retrieving evidence with ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<EvidenceDTO> getByEvidenceIds(List<String> evidenceIds) {
        try {
            List<Evidence> evidences = evidenceRepository.findAllById(evidenceIds);
            return evidences.stream()
                    .map(EvidenceMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error retrieving evidences by IDs: {}", evidenceIds, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void assignInvestResultByInvestigationPlanId(ResultInvestAssignedEvent event) {
        try {
            List<Evidence> evidences = evidenceRepository.findAllByInvestigationPlanIdAndIsDeletedFalse(event.getInvestigationPlanId());
            if (evidences == null) {
                Evidence evidence = new Evidence();
                evidence.setInvestigationPlanId(event.getInvestigationPlanId());
                evidence = evidenceRepository.save(evidence);
                createInvestResultByType(event, evidence.getEvidenceId());
            } else {
                for (Evidence evidence : evidences) {
                    createInvestResultByType(event, evidence.getEvidenceId());
                }
            }
        } catch (Exception e) {
            log.error("Error assigning investigation result for investigationPlanId: {}", event.getInvestigationPlanId(), e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void createInvestResultByType(ResultInvestAssignedEvent event, String evidenceId) {
        try {
            switch (event.getType()) {
                case "physical":
                    PhysicalInvestResultDTO physicalDTO = PhysicalInvestResultDTO.builder()
                            .imageUrl(event.getUploadFile())
                            .notes(event.getContent())
                            .build();
                    physicalInvestResultService.addPhysicalInvestResult(evidenceId, physicalDTO);
                    break;
                case "digital":
                    DigitalInvestResultDTO digitalDTO = DigitalInvestResultDTO.builder()
                            .imageUrl(event.getUploadFile())
                            .notes(event.getContent())
                            .build();
                    digitalInvestResultService.addDigitalInvestResult(evidenceId, digitalDTO);
                    break;
                case "financial":
                    FinancialInvestResultDTO financialDTO = FinancialInvestResultDTO.builder()
                            .imageUrl(event.getUploadFile())
                            .notes(event.getContent())
                            .build();
                    financialInvestResultService.addFinancialInvestResult(evidenceId, financialDTO);
                    break;
                case "forensic":
                    ForensicInvestResultDTO forensicDTO = ForensicInvestResultDTO.builder()
                            .imageUrl(event.getUploadFile())
                            .notes(event.getContent())
                            .build();
                    forensicInvestResultService.addForensicInvestResult(evidenceId, forensicDTO);
                    break;
                default:
                    log.warn("Unknown investigation type: {}", event.getType());
                    throw new AppException(ErrorCode.INVALID_INVESTIGATION_TYPE);
            }
        } catch (Exception e) {
            log.error("Error creating investigation result for evidenceId: {}", evidenceId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteInvestResultByInvestigationPlanId(String investigationPlanId, String type) {
        try {
            List<Evidence> evidences = evidenceRepository.findAllByInvestigationPlanIdAndIsDeletedFalse(investigationPlanId);
            if (evidences == null) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            for (Evidence evidence : evidences) {
                switch (type) {
                    case "physical":
                        physicalInvestResultService.deleteByEvidenceId(evidence.getEvidenceId());
                        break;
                    case "digital":
                        digitalInvestResultService.deleteByEvidenceId(evidence.getEvidenceId());
                        break;
                    case "financial":
                        financialInvestResultService.deleteByEvidenceId(evidence.getEvidenceId());
                        break;
                    case "forensic":
                        forensicInvestResultService.deleteByEvidenceId(evidence.getEvidenceId());
                        break;
                    default:
                        log.warn("Unknown investigation type: {}", type);
                        throw new AppException(ErrorCode.INVALID_INVESTIGATION_TYPE);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting investigation results for investigationPlanId: {}", investigationPlanId, e);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByEvidenceId(String evidenceId) {
        try {
            log.info("Checking if evidence exists with ID: {}", evidenceId);
            return evidenceRepository.existsByEvidenceIdAndIsDeletedFalse(evidenceId);
        } catch (Exception ex) {
            log.error("Error checking evidence existence with ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteByReportId(String reportId) {
        try {
            log.info("Deleting evidence by report ID: {}", reportId);
            List<ReportEvidence> reportEvidences = reportEvidenceRepository.findAllByReportIdAndIsDeletedFalse(reportId);
            if (reportEvidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            reportEvidences.forEach(reportEvidence -> {
                reportEvidence.setDeleted(true);
                reportEvidenceRepository.save(reportEvidence);
            });
            log.info("Successfully deleted evidence for report ID: {}", reportId);
        } catch (Exception ex) {
            log.error("Failed to delete evidence by report ID {}: {}", reportId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteByCaseId(String caseId) {
        try {
            log.info("Deleting evidence by case ID: {}", caseId);
            List<CaseEvidence> caseEvidences = caseEvidenceRepository.findAllByCaseIdAndIsDeletedFalse(caseId);
            if (caseEvidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            caseEvidences.forEach(caseEvidence -> {
                caseEvidence.setDeleted(true);
                caseEvidenceRepository.save(caseEvidence);
            });
            log.info("Successfully deleted evidence for case ID: {}", caseId);
        } catch (Exception ex) {
            log.error("Failed to delete evidence by case ID {}: {}", caseId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteByWarrantId(String warrantId) {
        try {
            log.info("Deleting evidence by warrant ID: {}", warrantId);
            List<WarrantEvidence> warrantEvidences = warrantEvidenceRepository.findAllByWarrantIdAndIsDeletedFalse(warrantId);
            if (warrantEvidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            warrantEvidences.forEach(warrantEvidence -> {
                warrantEvidence.setDeleted(true);
                warrantEvidenceRepository.save(warrantEvidence);
            });
            log.info("Successfully deleted evidence for warrant ID: {}", warrantId);
        } catch (Exception ex) {
            log.error("Failed to delete evidence by warrant ID {}: {}", warrantId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void deleteBySuspectId(String suspectId) {
        try {
            log.info("Deleting evidence by suspect ID: {}", suspectId);
            List<SuspectEvidence> suspectEvidences = suspectEvidenceRepository.findAllBySuspectIdAndIsDeletedFalse(suspectId);
            if (suspectEvidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            suspectEvidences.forEach(suspectEvidence -> {
                suspectEvidence.setDeleted(true);
                suspectEvidenceRepository.save(suspectEvidence);
            });
            log.info("Successfully deleted evidence for suspect ID: {}", suspectId);
        } catch (Exception ex) {
            log.error("Failed to delete evidence by suspect ID {}: {}", suspectId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Cacheable(value = "evidences", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<EvidenceDTO> getAllEvidence(Pageable pageable) {
        try {
            log.info("Retrieving all evidence with pagination");
            Page<EvidenceDTO> result = evidenceRepository.findAllNotDeleted(pageable)
                    .map(EvidenceMapper::toDTO);
            log.info("Successfully retrieved {} evidence items", result.getTotalElements());
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve all evidence: {}", ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private Evidence getEvidenceOrThrow(String evidenceId) {
        log.info("Checking evidence existence for ID: {}", evidenceId);
        return evidenceRepository.findByEvidenceIdAndIsDeletedFalse(evidenceId)
                .orElseThrow(() -> {
                    log.warn("Evidence not found: {}", evidenceId);
                    return new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
                });
    }

    @Override
    public List<SuspectDTO> getSuspectsByEvidence(String evidenceId) {
        try {
            log.info("Retrieving suspects for evidence ID: {}", evidenceId);
            List<String> suspectIds = suspectEvidenceRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId)
                    .stream()
                    .map(SuspectEvidence::getSuspectId)
                    .toList();
            List<SuspectDTO> result = suspectClient.getSuspectByIds(suspectIds);
            log.info("Successfully retrieved {} suspects for evidence ID: {}", result.size(), evidenceId);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve suspects for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<WarrantDTO> getWarrantsByEvidence(String evidenceId) {
        try {
            log.info("Retrieving warrants for evidence ID: {}", evidenceId);
            List<String> warrantIds = warrantEvidenceRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId)
                    .stream()
                    .map(WarrantEvidence::getWarrantId)
                    .toList();
            List<WarrantDTO> result = warrantClient.getWarrantByIds(warrantIds);
            log.info("Successfully retrieved {} warrants for evidence ID: {}", result.size(), evidenceId);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve warrants for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CaseDTO> getCasesByEvidence(String evidenceId) {
        try {
            log.info("Retrieving cases for evidence ID: {}", evidenceId);
            List<String> caseIds = caseEvidenceRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId)
                    .stream()
                    .map(CaseEvidence::getCaseId)
                    .toList();
            List<CaseDTO> result = caseClient.getCasesByIds(caseIds);
            log.info("Successfully retrieved {} cases for evidence ID: {}", result.size(), evidenceId);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve cases for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ReportDTO> getReportsByEvidence(String evidenceId) {
        try {
            log.info("Retrieving reports for evidence ID: {}", evidenceId);
            List<String> reportIds = reportEvidenceRepository.findAllByEvidenceIdAndIsDeletedFalse(evidenceId)
                    .stream()
                    .map(ReportEvidence::getReportId)
                    .toList();
            List<ReportDTO> result = reportClient.getReportsByIds(reportIds);
            log.info("Successfully retrieved {} reports for evidence ID: {}", result.size(), evidenceId);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve reports for evidence ID {}: {}", evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void removeSuspectFromEvidence(String suspectId) {
        try {
            log.info("Removing suspect {} from evidence", suspectId);
            List<SuspectEvidence> suspectEvidenceList = suspectEvidenceRepository.findAllBySuspectIdAndIsDeletedFalse(suspectId);
            if (suspectEvidenceList.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            suspectEvidenceList.forEach(suspectEvidence -> {
                suspectEvidence.setDeleted(true);
                suspectEvidence.setDetachedAt(LocalDateTime.now());
                suspectEvidenceRepository.save(suspectEvidence);
            });
            log.info("Successfully removed suspect {} from evidence", suspectId);
        } catch (Exception ex) {
            log.error("Failed to remove suspect {} from evidence: {}", suspectId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void saveEvidenceFromEvent(EvidenceCreatedEvent event) {
        try {
            log.info("Saving evidence from event with ID: {}", event.getEvidenceId());
            Evidence entity = Evidence.builder()
                    .evidenceId(event.getEvidenceId())
                    .status(EvidenceStatus.APPROVED)
                    .currentLocation(event.getLocation())
                    .collectorUsername(event.getCollector_username())
                    .build();
            evidenceRepository.save(entity);
            eventPublisher.send("evidence.created", event);
            log.info("Successfully saved evidence from event with ID: {}", event.getEvidenceId());
        } catch (Exception ex) {
            log.error("Failed to save evidence from event with ID {}: {}", event.getEvidenceId(), ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void updateAnalysisResult(AnalysisResultEvent event) {
        try {
            log.info("Updating analysis result for evidence ID: {}", event.getEvidenceId());
            Evidence evidence = getEvidenceOrThrow(event.getEvidenceId());
            evidence.setMeasureSurveyId(event.getMeasureSurveyId());
            evidenceRepository.save(evidence);
            log.info("Successfully updated analysis result for evidence ID: {}", event.getEvidenceId());
        } catch (Exception ex) {
            log.error("Failed to update analysis result for evidence ID {}: {}", event.getEvidenceId(), ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void assignSuspectToEvidence(String evidenceId, String suspectId) {
        try {
            log.info("Assigning suspect {} to evidence {}", suspectId, evidenceId);
            SuspectEvidence suspectEvidence = new SuspectEvidence();
            suspectEvidence.setEvidenceId(evidenceId);
            suspectEvidence.setSuspectId(suspectId);
            suspectEvidenceRepository.save(suspectEvidence);
            log.info("Successfully assigned suspect {} to evidence {}", suspectId, evidenceId);
        } catch (Exception ex) {
            log.error("Failed to assign suspect {} to evidence {}: {}", suspectId, evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void assignCaseToEvidence(String evidenceId, String caseId) {
        try {
            log.info("Assigning case {} to evidence {}", caseId, evidenceId);
            CaseEvidence caseEvidence = new CaseEvidence();
            caseEvidence.setEvidenceId(evidenceId);
            caseEvidence.setCaseId(caseId);
            caseEvidenceRepository.save(caseEvidence);
            log.info("Successfully assigned case {} to evidence {}", caseId, evidenceId);
        } catch (Exception ex) {
            log.error("Failed to assign case {} to evidence {}: {}", caseId, evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void assignWarrantToEvidence(String evidenceId, String warrantId) {
        try {
            log.info("Assigning warrant {} to evidence {}", warrantId, evidenceId);
            WarrantEvidence warrantEvidence = new WarrantEvidence();
            warrantEvidence.setEvidenceId(evidenceId);
            warrantEvidence.setWarrantId(warrantId);
            warrantEvidenceRepository.save(warrantEvidence);
            log.info("Successfully assigned warrant {} to evidence {}", warrantId, evidenceId);
        } catch (Exception ex) {
            log.error("Failed to assign warrant {} to evidence {}: {}", warrantId, evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void assignReportToEvidence(String evidenceId, String reportId) {
        try {
            log.info("Assigning report {} to evidence {}", reportId, evidenceId);
            ReportEvidence reportEvidence = new ReportEvidence();
            reportEvidence.setEvidenceId(evidenceId);
            reportEvidence.setReportId(reportId);
            reportEvidenceRepository.save(reportEvidence);
            log.info("Successfully assigned report {} to evidence {}", reportId, evidenceId);
        } catch (Exception ex) {
            log.error("Failed to assign report {} to evidence {}: {}", reportId, evidenceId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = {"evidence", "evidenceByCaseSuspect"}, allEntries = true)
    public void softDeleteByMeasureSurveyId(String measureSurveyId) {
        try {
            log.info("Soft deleting evidence by measure survey ID: {}", measureSurveyId);
            List<Evidence> evidences = evidenceRepository.findAllByMeasureSurveyIdAndIsDeletedFalse(measureSurveyId);
            if (evidences.isEmpty()) {
                throw new AppException(ErrorCode.EVIDENCE_NOT_FOUND);
            }
            for (Evidence evidence : evidences) {
                evidence.setDeleted(true);
                evidenceRepository.save(evidence);
            }
            log.info("Successfully soft deleted evidence for measure survey ID: {}", measureSurveyId);
        } catch (Exception ex) {
            log.error("Failed to soft delete evidence by measure survey ID {}: {}", measureSurveyId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByReportId(String reportId) {
        try {
            log.info("Checking if evidence exists for report ID: {}", reportId);
            return reportEvidenceRepository.existsByReportIdAndIsDeletedFalse(reportId);
        } catch (Exception ex) {
            log.error("Failed to check evidence existence for report ID {}: {}", reportId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByCaseId(String caseId) {
        try {
            log.info("Checking if evidence exists for case ID: {}", caseId);
            return caseEvidenceRepository.existsByCaseIdAndIsDeletedFalse(caseId);
        } catch (Exception ex) {
            log.error("Failed to check evidence existence for case ID {}: {}", caseId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsBySuspectId(String suspectId) {
        try {
            log.info("Checking if evidence exists for suspect ID: {}", suspectId);
            return suspectEvidenceRepository.existsBySuspectIdAndIsDeletedFalse(suspectId);
        } catch (Exception ex) {
            log.error("Failed to check evidence existence for suspect ID {}: {}", suspectId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean existsByWarrantId(String warrantId) {
        try {
            log.info("Checking if evidence exists for warrant ID: {}", warrantId);
            return warrantEvidenceRepository.existsByWarrantIdAndIsDeletedFalse(warrantId);
        } catch (Exception ex) {
            log.error("Failed to check evidence existence for warrant ID {}: {}", warrantId, ex.getMessage(), ex);
            throw new AppException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}