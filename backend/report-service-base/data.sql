USE report_services;

-- Create the table with the correct schema
DROP TABLE IF EXISTS report;

CREATE TABLE report (
    report_id VARCHAR(255) NOT NULL,
    case_id VARCHAR(255),
    type_report VARCHAR(255),
    severity VARCHAR(255),
    description VARCHAR(255),
    case_location VARCHAR(255),
    reported_at DATETIME(6),
    reporter_fullname VARCHAR(255),
    reporter_email VARCHAR(255),
    reporter_phonenumber VARCHAR(255),
    status VARCHAR(255),
    officer_approve_username VARCHAR(255),
    is_deleted BIT DEFAULT 0,
    PRIMARY KEY (report_id)
) ENGINE=InnoDB;

-- Insert sample data into the report table
INSERT INTO report (report_id, case_id, type_report, severity, description, case_location, reported_at, reporter_fullname, reporter_email, reporter_phonenumber, status, officer_approve_username, is_deleted)
VALUES
('550e8400-e29b-41d4-a716-446655440001', 'CASE001', 'Traffic Accident', 'High', 'Car collision at intersection', '123 Main St, City A', '2025-07-13 08:30:00', 'John Doe', 'john.doe@example.com', '555-0101', 'PENDING', 'officer1', false),
('550e8400-e29b-41d4-a716-446655440002', 'CASE002', 'Theft', 'Medium', 'Stolen bicycle from park', '456 Park Ave, City B', '2025-07-12 14:15:00', 'Jane Smith', 'jane.smith@example.com', '555-0102', 'APPROVED', 'officer2', false),
('550e8400-e29b-41d4-a716-446655440003', 'CASE003', 'Vandalism', 'Low', 'Graffiti on public property', '789 Elm St, City C', '2025-07-11 09:00:00', 'Alice Johnson', 'alice.j@example.com', '555-0103', 'REJECTED', 'officer3', false),
('550e8400-e29b-41d4-a716-446655440004', 'CASE004', 'Noise Complaint', 'Low', 'Loud music from neighbor', '101 Oak Rd, City D', '2025-07-10 22:45:00', 'Bob Brown', 'bob.brown@example.com', '555-0104', 'PENDING', null, false),
('550e8400-e29b-41d4-a716-446655440005', 'CASE005', 'Assault', 'Critical', 'Physical altercation in bar', '202 Pine St, City E', '2025-07-09 23:30:00', 'Emma Wilson', 'emma.w@example.com', '555-0105', 'IN_PROGRESS', 'officer4', false);