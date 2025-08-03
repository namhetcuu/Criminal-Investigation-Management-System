/*
 * @ (#) ResourceNotFoundException.java 1.0 7/9/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.exception;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/9/2025
 * @version 1.0
 */
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(HttpStatus.NOT_FOUND, String.format("Not found %s with %s = '%s'", resourceName, fieldName, fieldValue));
    }
}