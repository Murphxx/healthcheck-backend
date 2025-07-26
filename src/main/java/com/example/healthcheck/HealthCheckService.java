package com.example.healthcheck;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Service
public class HealthCheckService {
    private final Map<ModuleHealth, HealthResponse> statusMap = new EnumMap<>(ModuleHealth.class);
    private final RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        for (ModuleHealth module : ModuleHealth.values()) {
            statusMap.put(module, null);
        }
    }

    @Scheduled(fixedRate = 30000) // 30 saniyede bir
    public void checkAllModules() {
        for (ModuleHealth module : ModuleHealth.values()) {
            checkModule(module);
        }
    }

    public void checkModule(ModuleHealth module) {
        try {
            ResponseEntity<HealthResponse> response;
            if ("POST".equalsIgnoreCase(module.getHttpMethod())) {
                response = restTemplate.postForEntity(module.getHealthEndpoint(), null, HealthResponse.class);
            } else {
                response = restTemplate.getForEntity(module.getHealthEndpoint(), HealthResponse.class);
            }
            statusMap.put(module, response.getBody());
        } catch (Exception e) {
            // Ulaşılamıyorsa boş HealthResponse ile işaretle
            HealthResponse down = new HealthResponse();
            down.setStatus(503);
            down.setService(module.getDisplayName());
            down.setTimestamp(java.time.Instant.now().toString());
            down.setJar_status("ERROR");
            statusMap.put(module, down);
        }
    }

    public Map<ModuleHealth, HealthResponse> getAllStatuses() {
        return statusMap;
    }
}