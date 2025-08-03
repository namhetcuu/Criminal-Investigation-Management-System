-- Creating database
CREATE DATABASE IF NOT EXISTS evidence_service;
USE evidence_service;

-- Creating table for BaseClass (fields inherited by other tables)
-- Note: BaseClass fields (created_at, updated_at, is_deleted) are included in child tables

-- Table: case_evidence
CREATE TABLE case_evidence (
    case_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (case_id, evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: evidence
CREATE TABLE evidence (
    evidence_id VARCHAR(36) NOT NULL,
    case_id VARCHAR(36),
    report_id VARCHAR(36),
    warrantResult_id VARCHAR(36),
    measure_survey_id VARCHAR(36),
    investigation_plan_id VARCHAR(36),
    description TEXT,
    collector_username VARCHAR(255),
    detailed_description TEXT,
    initial_condition TEXT,
    preservation_measures TEXT,
    location_at_scene TEXT,
    current_location TEXT,
    attached_file VARCHAR(255),
    status ENUM('PENDING', 'WAITING', 'APPROVED', 'DELETED', 'ASSIGNED') NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: digital_invest_result
CREATE TABLE digital_invest_result (
    result_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    device_type VARCHAR(255),
    analyst_tool VARCHAR(255),
    result TEXT,
    upload_file VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (result_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: financial_invest_result
CREATE TABLE financial_invest_result (
    result_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    summary TEXT,
    attached_file VARCHAR(255),
    upload_file VARCHAR(255),
    result TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (result_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: forensic_invest_result
CREATE TABLE forensic_invest_result (
    result_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    lab_name VARCHAR(255),
    report TEXT,
    result_summary TEXT,
    upload_file VARCHAR(255),
    result TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (result_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: measure_survey
CREATE TABLE measure_survey (
    measure_survey_id VARCHAR(36) NOT NULL,
    type_name VARCHAR(255),
    source VARCHAR(255),
    result_id VARCHAR(36),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (measure_survey_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: physical_invest_result
CREATE TABLE physical_invest_result (
    result_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    status ENUM('WAITING', 'PROTECTED') NOT NULL,
    notes TEXT,
    image_url VARCHAR(255),
    upload_file VARCHAR(255),
    result TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (result_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: record_info
CREATE TABLE record_info (
    record_info_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    type_name VARCHAR(255),
    source VARCHAR(255),
    summary TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (record_info_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: report_evidence
CREATE TABLE report_evidence (
    report_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    attached_by VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (report_id, evidence_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: suspect_evidence
CREATE TABLE suspect_evidence (
    suspect_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    detached_at DATETIME,
    attached_by VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (suspect_id, evidence_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: warrant_evidence
CREATE TABLE warrant_evidence (
    warrant_id VARCHAR(36) NOT NULL,
    evidence_id VARCHAR(36) NOT NULL,
    assigned_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (warrant_id, evidence_id),
    FOREIGN KEY (evidence_id) REFERENCES evidence(evidence_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample data

-- Insert into evidence
INSERT INTO evidence (
    evidence_id, case_id, report_id, warrantResult_id, measure_survey_id, investigation_plan_id,
    description, collector_username, detailed_description, initial_condition, preservation_measures,
    location_at_scene, current_location, attached_file, status, created_at, updated_at, is_deleted
) VALUES (
    'evidence1', 'case1', NULL, NULL, NULL, NULL,
    'Laptop seized from suspect', 'user1', 'Dell XPS 13, Serial: ABC123',
    'Good condition', 'Stored in secure locker', 'Living room', 'Evidence locker A',
    'evidence1.pdf', 'APPROVED', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
), (
    'evidence2', 'case2', NULL, NULL, NULL, NULL,
    'USB drive found at crime scene', 'user2', 'SanDisk 64GB, Serial: XYZ789',
    'Intact', 'Sealed in evidence bag', 'Bedroom', 'Evidence locker B',
    'evidence2.pdf', 'PENDING', '2025-07-11 12:01:00', '2025-07-11 12:01:00', FALSE
);

-- Insert into case_evidence
INSERT INTO case_evidence (
    case_id, evidence_id, created_at, updated_at, is_deleted
) VALUES (
    'case1', 'evidence1', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
), (
    'case2', 'evidence2', '2025-07-11 12:01:00', '2025-07-11 12:01:00', FALSE
);

-- Insert into digital_invest_result
INSERT INTO digital_invest_result (
    result_id, evidence_id, device_type, analyst_tool, result, upload_file,
    created_at, updated_at, is_deleted
) VALUES (
    'result1', 'evidence1', 'Laptop', 'ToolX', 'Recovered deleted files', 'http://example.com/image1.jpg',
    '2025-07-11 12:11:00', '2025-07-11 12:11:00', FALSE
), (
    'result2', 'evidence2', 'USB Drive', 'ToolY', 'Extracted encrypted data', 'http://example.com/image2.jpg',
    '2025-07-11 12:12:00', '2025-07-11 12:12:00', FALSE
);

-- Insert into report_evidence
INSERT INTO report_evidence (
    report_id, evidence_id, attached_by, created_at, updated_at, is_deleted
) VALUES (
    'report1', 'evidence1', 'user1', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
);

-- Insert into suspect_evidence
INSERT INTO suspect_evidence (
    suspect_id, evidence_id, detached_at, attached_by, created_at, updated_at, is_deleted
) VALUES (
    'suspect1', 'evidence1', NULL, 'user1', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
);

-- Insert into warrant_evidence
INSERT INTO warrant_evidence (
    warrant_id, evidence_id, assigned_at, created_at, updated_at, is_deleted
) VALUES (
    'warrant1', 'evidence1', '2025-07-11 12:00:00', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
);

-- Insert into measure_survey
INSERT INTO measure_survey (
    measure_survey_id, type_name, source, result_id, created_at, updated_at, is_deleted
) VALUES (
    'measure1', 'SurveyType1', 'Source1', 'result1', '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
);

-- Insert into physical_invest_result
INSERT INTO physical_invest_result (
    result_id, evidence_id, status, notes, image_url, upload_file, result,
    created_at, updated_at, is_deleted
) VALUES (
    'phy_result1', 'evidence1', 'PROTECTED', 'Physical evidence secured', 'http://example.com/phy_image1.jpg',
    'phy_file1.pdf', 'Physical analysis completed', '2025-07-11 12:11:00', '2025-07-11 12:11:00', FALSE
);

-- Insert into financial_invest_result
INSERT INTO financial_invest_result (
    result_id, evidence_id, summary, attached_file, upload_file, result,
    created_at, updated_at, is_deleted
) VALUES (
    'fin_result1', 'evidence1', 'Financial transaction analysis', 'fin_file1.pdf', 'http://example.com/fin_file1.pdf',
    'Suspicious transactions detected', '2025-07-11 12:11:00', '2025-07-11 12:11:00', FALSE
);

-- Insert into forensic_invest_result
INSERT INTO forensic_invest_result (
    result_id, evidence_id, lab_name, report, result_summary, upload_file, result,
    created_at, updated_at, is_deleted
) VALUES (
    'for_result1', 'evidence1', 'LabX', 'Forensic analysis report', 'DNA evidence confirmed',
    'http://example.com/for_file1.pdf', 'Positive match', '2025-07-11 12:11:00', '2025-07-11 12:11:00', FALSE
);

-- Insert into record_info
INSERT INTO record_info (
    record_info_id, evidence_id, type_name, source, summary, created_at, updated_at, is_deleted
) VALUES (
    'record1', 'evidence1', 'RecordType1', 'Source1', 'Summary of evidence record',
    '2025-07-11 12:00:00', '2025-07-11 12:00:00', FALSE
);