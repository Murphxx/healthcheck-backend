package com.example.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    private final HealthCheckService healthCheckService;

    public StatusController(HealthCheckService healthCheckService) {
        this.healthCheckService = healthCheckService;
    }

    @GetMapping
    public Collection<HealthResponse> getAllStatuses() {
        return healthCheckService.getAllStatuses().values();
    }

    @GetMapping("/check")
    public String checkAllModules() {
        healthCheckService.checkAllModules();
        return "All modules checked successfully";
    }
}