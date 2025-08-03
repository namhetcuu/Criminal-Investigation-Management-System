package com.Evidence_Service.client;

import com.Evidence_Service.config.FeignConfig;
import com.Evidence_Service.dto.CaseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@FeignClient(name = "case-service", url = "${case-service.url}", configuration = FeignConfig.class)
public interface CaseClient {

    @PostMapping("/api/cases/by-ids")
    List<CaseDTO> getCasesByIds(@RequestBody List<String> ids);
}


