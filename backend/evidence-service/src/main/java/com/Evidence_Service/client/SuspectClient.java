package com.Evidence_Service.client;

import com.Evidence_Service.config.FeignConfig;
import com.Evidence_Service.dto.CaseDTO;
import com.Evidence_Service.dto.SuspectDTO;
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

@FeignClient(name = "suspect-service", url = "${suspect-service.url}", configuration = FeignConfig.class)
public interface SuspectClient {

    @PostMapping("/api/suspects/by-ids")
    List<SuspectDTO> getSuspectByIds(@RequestBody List<String> ids);
}
