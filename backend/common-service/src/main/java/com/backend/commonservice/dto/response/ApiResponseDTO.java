/*
 * @ (#) ApiResponseDTO.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.commonservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/7/2025
 * @version:    1.0
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO<T> {
    int code;
    String message;
    T errors;
}
