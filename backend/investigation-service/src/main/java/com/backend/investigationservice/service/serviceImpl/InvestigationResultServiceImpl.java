package com.backend.investigationservice.service.serviceImpl;

import com.backend.investigationservice.service.InvestigationResultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class InvestigationResultServiceImpl implements InvestigationResultService {

    @Override
    public void saveDigitalResult(String result) {
        log.info("Saving Digital Result: {}", result);
        // TODO: Ghi vào bảng kết quả số (DigitalResult entity), hoặc lưu vào InvestigationPlan nếu dùng chung
    }

    @Override
    public void updateDigitalResult(String result) {
        log.info("Updating Digital Result: {}", result);
        // TODO: Update entity trong DB
    }

    @Override
    public void savePhysicalResult(String result) {
        log.info("Saving Physical Result: {}", result);
        // TODO
    }

    @Override
    public void updatePhysicalResult(String result) {
        log.info("Updating Physical Result: {}", result);
        // TODO
    }

    @Override
    public void saveFinancialResult(String result) {
        log.info("Saving Financial Result: {}", result);
        // TODO
    }

    @Override
    public void updateFinancialResult(String result) {
        log.info("Updating Financial Result: {}", result);
        // TODO
    }

    @Override
    public void saveForensicResult(String result) {
        log.info("Saving Forensic Result: {}", result);
        // TODO
    }

    @Override
    public void updateForensicResult(String result) {
        log.info("Updating Forensic Result: {}", result);
        // TODO
    }
}