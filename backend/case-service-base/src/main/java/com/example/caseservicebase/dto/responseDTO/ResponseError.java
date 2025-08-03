package com.example.caseservicebase.dto.responseDTO;

public class ResponseError extends ResponseData {

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
