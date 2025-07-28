package com.example.healthcheck;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import jakarta.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;

@Service
public class HealthCheckService {
    private final Map<ModuleHealth, HealthResponse> statusMap = new EnumMap<>(ModuleHealth.class);
    private final RestTemplate restTemplate;

    public HealthCheckService() {
        this.restTemplate = new RestTemplate();
        // Timeout ayarları
        this.restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("User-Agent", "HealthCheck-Bot/1.0");
            return execution.execute(request, body);
        });
        
        // Timeout ayarları
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 saniye
        factory.setReadTimeout(30000); // 30 saniye
        this.restTemplate.setRequestFactory(factory);
    }

    @PostConstruct
    public void init() {
        for (ModuleHealth module : ModuleHealth.values()) {
            statusMap.put(module, null);
        }
    }

    @Scheduled(fixedRate = 30000) // 30 saniyede bir
    public void checkAllModules() {
        System.out.println("=== Starting scheduled health check ===");
        for (ModuleHealth module : ModuleHealth.values()) {
            System.out.println("Checking module: " + module.getDisplayName());
            checkModule(module);
        }
        System.out.println("=== Health check completed ===");
    }

    public void checkModule(ModuleHealth module) {
        try {
            // Web Service için özel işlem
            if (module == ModuleHealth.WEB_SERVICE) {
                System.out.println("Checking Web Service: " + module.getHealthEndpoint());
                ResponseEntity<String> response = restTemplate.getForEntity(module.getHealthEndpoint(), String.class);
                String body = response.getBody();
                System.out.println("Web Service Response Status: " + response.getStatusCode());
                System.out.println("Web Service Response Body: " + body);
                
                // HTTP 200 OK ise çalışıyor kabul et (HTML döndürse bile)
                if (response.getStatusCode().is2xxSuccessful()) {
                    HealthResponse up = new HealthResponse();
                    up.setStatus(200);
                    up.setService(module.getDisplayName());
                    up.setTimestamp(java.time.Instant.now().toString());
                    up.setJar_status("RUNNING");
                    up.setCpu_percent(0.0);
                    up.setMemory_percent(0.0);
                    up.setDisk_percent(0.0);
                    up.setUptime("N/A");
                    statusMap.put(module, up);
                } else {
                    throw new Exception("Invalid response");
                }
            } 
            // AOM ve alt modülleri için özel işlem
            else if (module == ModuleHealth.AOM || module == ModuleHealth.ORACLE_DB || 
                     module == ModuleHealth.HAZELCAST || module == ModuleHealth.VOLTDB) {
                
                System.out.println("Checking AOM Service: " + module.getHealthEndpoint());
                ResponseEntity<AOMResponse> response = restTemplate.getForEntity(module.getHealthEndpoint(), AOMResponse.class);
                AOMResponse aomResponse = response.getBody();
                System.out.println("AOM Response Status: " + response.getStatusCode());
                System.out.println("AOM Response Body: " + aomResponse);
                
                if (aomResponse != null && aomResponse.getStatus() == 200) {
                    HealthResponse healthResponse = new HealthResponse();
                    healthResponse.setStatus(200);
                    healthResponse.setTimestamp(aomResponse.getTimestamp());
                    healthResponse.setCpu_percent(aomResponse.getCpu_percent());
                    healthResponse.setMemory_percent(aomResponse.getMemory_percent());
                    healthResponse.setDisk_percent(aomResponse.getDisk_percent());
                    healthResponse.setUptime(aomResponse.getUptime());
                    
                    // Modüle göre özel işlem
                    switch (module) {
                        case AOM:
                            healthResponse.setService("AOM");
                            healthResponse.setJar_status(aomResponse.getJar_status());
                            break;
                        case ORACLE_DB:
                            healthResponse.setService("Oracle Database");
                            int oracleHealth = aomResponse.getOracle_health();
                            if (oracleHealth >= 95) {
                                healthResponse.setStatus(200);
                                healthResponse.setJar_status("RUNNING");
                            } else {
                                healthResponse.setStatus(400);
                                healthResponse.setJar_status("WARNING");
                            }
                            // Oracle için sadece temel bilgiler, diğer değerler 0
                            healthResponse.setCpu_percent(0.0);
                            healthResponse.setMemory_percent(0.0);
                            healthResponse.setDisk_percent(0.0);
                            break;
                        case HAZELCAST:
                            healthResponse.setService("Hazelcast");
                            int hazelcastHealth = aomResponse.getHazelcast_health();
                            if (hazelcastHealth >= 95) {
                                healthResponse.setStatus(200);
                                healthResponse.setJar_status("RUNNING");
                            } else {
                                healthResponse.setStatus(400);
                                healthResponse.setJar_status("WARNING");
                            }
                            // Hazelcast için sadece temel bilgiler, diğer değerler 0
                            healthResponse.setCpu_percent(0.0);
                            healthResponse.setMemory_percent(0.0);
                            healthResponse.setDisk_percent(0.0);
                            break;
                        case VOLTDB:
                            healthResponse.setService("VoltDB");
                            int voltdbHealth = aomResponse.getVoltdb_health();
                            if (voltdbHealth >= 95) {
                                healthResponse.setStatus(200);
                                healthResponse.setJar_status("RUNNING");
                            } else {
                                healthResponse.setStatus(400);
                                healthResponse.setJar_status("WARNING");
                            }
                            // VoltDB için sadece temel bilgiler, diğer değerler 0
                            healthResponse.setCpu_percent(0.0);
                            healthResponse.setMemory_percent(0.0);
                            healthResponse.setDisk_percent(0.0);
                            break;
                    }
                    
                    statusMap.put(module, healthResponse);
                } else {
                    throw new Exception("AOM service not available");
                }
                        } else {
                // Diğer modüller için normal işlem
                System.out.println("Checking module: " + module.getDisplayName() + " at " + module.getHealthEndpoint());
                ResponseEntity<HealthResponse> response;
                if ("POST".equalsIgnoreCase(module.getHttpMethod())) {
                    response = restTemplate.postForEntity(module.getHealthEndpoint(), null, HealthResponse.class);
                } else {
                    response = restTemplate.getForEntity(module.getHealthEndpoint(), HealthResponse.class);
                }
                System.out.println("Response for " + module.getDisplayName() + ": " + response.getStatusCode());
                statusMap.put(module, response.getBody());
            }
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