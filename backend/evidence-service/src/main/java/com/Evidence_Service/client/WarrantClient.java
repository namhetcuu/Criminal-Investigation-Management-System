package com.Evidence_Service.client;

import com.Evidence_Service.config.FeignConfig;
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

@FeignClient(name = "warrant-service", url = "${warrant-service.url}", configuration = FeignConfig.class)
public interface WarrantClient {

    @PostMapping("/api/warrants/by-ids")
    List<WarrantDTO> getWarrantByIds(@RequestBody List<String> ids);
}

