/*
 * @ (#) TimelineRequestDTO.java 1.0 7/10/2025
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
public class TimelineRequestDTO {
    private Long timelineId;
    private Long holidayId;
    private Long caseResultId;
    private String startTime;
    private String endTime;
    private String attachedFile;
    private String notes;

    private String activity;
    private String holidayConflict;
}
