package com.example.caseservicebase.dto.requestDTO;

import lombok.Getter;

@Getter
public class CaseRequestDTO {

    private Long caseId;

    private String caseNumber;

    private String typeCase;

    private String severity;

    private String status;

    private String summary;

    private String caseTarget;

}
