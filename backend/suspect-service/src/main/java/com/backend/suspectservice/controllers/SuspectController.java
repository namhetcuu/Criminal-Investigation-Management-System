/*
 * @ (#) SuspectController.java  1.0 7/7/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.controllers;

import com.backend.suspectservice.dto.request.SuspectCreateRequest;
import com.backend.suspectservice.dto.response.SuspectResponse;
import com.backend.suspectservice.service.SuspectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/7/2025
 * @version:    1.0
 */
@Slf4j
@RestController
@RequestMapping("/suspects")
@Tag(name = "Suspect Query", description = "Suspect API")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SuspectController {
    SuspectService suspectService;

    public SuspectController(SuspectService suspectService) {
        this.suspectService = suspectService;
    }

    /*
     * @method: getAllSuspects
     * @description: Get all suspects
     * @return: List of SuspectResponse
     */
    @Operation(summary = "Get all Suspects", description = "Retrieve a list of all suspects")
    @GetMapping
    public ResponseEntity<List<SuspectResponse>> getAllSuspects() {
        log.info("Get all Suspects request");
        return ResponseEntity.status(HttpStatus.OK).body(suspectService.getAllSuspects());
    }

    /*
     * @method: Create a new Suspect
     * @description: Create a new suspect with a mugshot image and details
     */
    @Operation(
            summary = "Create a new Suspect",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(mediaType = "multipart/form-data",
                                    encoding = {
                                            @Encoding(name = "mugshotUrl", contentType = "image/png, image/jpeg"), // Cho file
                                            @Encoding(name = "request", contentType = "application/json")      // Cho object JSON
                                    })
                    }
            )
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuspectResponse> createSuspect(
            @Schema(type = "string", format = "binary",  description = "Suspect mugshot image")
            @RequestPart(name = "mugshotUrl") MultipartFile suspectImage,

            @Schema(implementation = SuspectCreateRequest.class)
            @RequestPart(name = "request") @Valid SuspectCreateRequest suspectCreateRequest
    ) {
        log.info("Create Suspect request content: {}", suspectCreateRequest);
        SuspectResponse c = suspectService.createSuspect(suspectCreateRequest, suspectImage);
        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    /*
     * @method: getSuspectByStatus
     * @description: Get suspect by status
     * @param id: Suspect status
     * @return: SuspectResponse
     */
    @Operation(summary = "Get Suspects by Status", description = "Retrieve a list of suspects by their status"

    )
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SuspectResponse>> getSuspectByStatus(
            @Parameter(
                    description = "Suspect status",
                    schema = @Schema(type = "string", allowableValues = {"Interviewed", "Not Yet Catch", "Waiting for interview"})
            )
            @PathVariable("status") String status
    ) {
        log.info("Controller Get Suspects by status: {}", status);
        List<SuspectResponse> suspects = suspectService.getSuspectsByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(suspects);
    }

}
