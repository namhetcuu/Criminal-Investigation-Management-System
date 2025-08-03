/*
 * @ (#) CaseEvidenceRequestDTO.java 1.0 7/10/2025
 *
 * Copyright (c) 2025 IUH.All rights reserved
 */

package com.example.caseservicebase.dto.requestDTO;

import lombok.Data;

/*
 * @description
 * @author : Nguyen Truong An
 * @date : 7/10/2025
 * @version 1.0
 */
@Data
public class CaseEvidenceRequestDTO {
    private Long evidenceId;
    private Long caseId;
}
