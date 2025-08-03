package com.Evidence_Service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * The enum defines the error codes used in the Evidence Service system.
 */
@Getter
public enum ErrorCode {

    // ===== COMMON =====
    UNCATEGORIZED_EXCEPTION(9000, "Uncategorized server error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT(9001, "Invalid input data", HttpStatus.BAD_REQUEST),
    INVALID_KEY(9002, "Invalid message key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(9003, "Unauthorized access", HttpStatus.UNAUTHORIZED),
    METHOD_NOT_ALLOWED(9004, "Method not allowed", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE(9005, "Unsupported media type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    NOT_FOUND(404, "Resource not found", HttpStatus.NOT_FOUND),
    FORBIDDEN(403, "Access forbidden", HttpStatus.FORBIDDEN),
    BAD_GATEWAY(502, "Bad gateway", HttpStatus.BAD_GATEWAY),
    SERVICE_UNAVAILABLE(503, "Service unavailable", HttpStatus.SERVICE_UNAVAILABLE),
    INTERNAL_SERVER_ERROR(9999, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(400, "Bad request", HttpStatus.BAD_REQUEST),

    // ===== RECORD INFO =====
    RECORD_INFO_NOT_FOUND(4004, "Record info not found", HttpStatus.NOT_FOUND),

    // ===== INVESTIGATION RESULTS =====
    INVALID_INVESTIGATION_TYPE(110, "INVALID_INVESTIGATION_TYPE", HttpStatus.NOT_FOUND),
    DIGITAL_INVEST_RESULT_ALREADY_EXISTS(105, "DIGITAL_INVEST_RESULT_ALREADY_EXISTS", HttpStatus.BAD_REQUEST),
    DIGITAL_INVEST_RESULT_NOT_FOUND(100, "Digital investigation result not found", HttpStatus.NOT_FOUND),
    FORENSIC_INVEST_RESULT_NOT_FOUND(101, "Forensic investigation result not found", HttpStatus.NOT_FOUND),
    FINANCIAL_INVEST_RESULT_NOT_FOUND(111, "Financial investigation result not found", HttpStatus.NOT_FOUND),
    PHYSICAL_INVEST_RESULT_NOT_FOUND(112, "Physical investigation result not found", HttpStatus.NOT_FOUND),
    MEASURE_SURVEY_NOT_FOUND(113, "Measure survey not found", HttpStatus.NOT_FOUND),

    // ===== EVIDENCE =====
    EVIDENCE_NOT_FOUND(1000, "Evidence not found", HttpStatus.NOT_FOUND),
    DUPLICATE_EVIDENCE(1001, "Evidence already exists", HttpStatus.BAD_REQUEST),
    INVALID_EVIDENCE_TYPE(1002, "Invalid evidence type", HttpStatus.BAD_REQUEST),

    // ===== SUSPECT =====
    SUSPECT_NOT_FOUND(1100, "Suspect not found", HttpStatus.NOT_FOUND),
    SUSPECT_ALREADY_ASSIGNED(1101, "Suspect already assigned to evidence", HttpStatus.CONFLICT),

    // ===== CASE =====
    CASE_NOT_FOUND(1200, "Case not found", HttpStatus.NOT_FOUND),
    CASE_ALREADY_ASSIGNED(1201, "Evidence already assigned to case", HttpStatus.CONFLICT),

    // ===== WARRANT =====
    WARRANT_NOT_FOUND(1300, "Warrant not found", HttpStatus.NOT_FOUND),
    WARRANT_ALREADY_ASSIGNED(1301, "Warrant already assigned to evidence", HttpStatus.CONFLICT),

    // ===== FILE =====
    FILE_UPLOAD_FAILED(1400, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_TOO_LARGE(1401, "File size exceeds the allowed limit", HttpStatus.BAD_REQUEST),
    FILE_FORMAT_NOT_SUPPORTED(1402, "File format not supported", HttpStatus.UNSUPPORTED_MEDIA_TYPE),

    // ===== KAFKA =====
    KAFKA_PUBLISH_FAILED(1500, "Failed to publish Kafka message", HttpStatus.INTERNAL_SERVER_ERROR),
    KAFKA_CONSUME_ERROR(1501, "Error while consuming Kafka message", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
