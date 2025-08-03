/*
 * @ (#) CaseResultRequestDTO.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.dto.requestDTO;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */

import lombok.Data;

@Data
public class CaseResultRequestDTO {
    private Long caseResultId;
    private Long caseId;
    private String reportTime;
    private String reportAnalyst;
    private String summary;
    private String identifyMotive;
    private String status;
}
