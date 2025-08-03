package com.Evidence_Service.client;

import com.Evidence_Service.config.FeignConfig;
import com.Evidence_Service.dto.InvestigationDTO;
import com.Evidence_Service.dto.WarrantDTO;
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

@FeignClient(name = "investigation-service", url = "${investigation-service.url}", configuration = FeignConfig.class)
public interface InvestigationClient {

    @PostMapping("/api/investigations/by-ids")
    List<InvestigationDTO> getInvestigationByIds(@RequestBody List<String> ids);
}

