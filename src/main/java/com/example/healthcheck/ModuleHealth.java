package com.example.healthcheck;

public enum ModuleHealth {
    CHF("Charging Function", "http://35.241.210.255:8080/chf/health", "GET"),
    TGF("Traffic Generator", "http://34.44.173.88:5000/TGF/health", "GET"),
    CGF("Charging GW Function", "http://34.51.185.11:8080/CGF/health", "GET"),
    ABMF("ABMF", "http://35.242.142.244:8080/ABMF/health", "GET"),
    KAFKA("Kafka", "http://34.38.128.100:9010/kafka/health", "GET"),
    NOTIFICATION_SERVICE("Notification Service", "http://35.198.190.231:3000/api/NS/health", "GET"),
    SMS_APP("PRS ", "http://34.59.178.11:8080/health", "GET"),
    WEB_SERVICE("Web App", "http://35.187.54.5", "GET"),
    AOM("Account & Order Mng", "http://34.123.86.69/aom/health", "GET"),
    ORACLE_DB("Oracle Database", "http://34.123.86.69/aom/health", "GET"),
    HAZELCAST("Hazelcast", "http://34.123.86.69/aom/health", "GET"),
    VOLTDB("Volt Active Data", "http://34.123.86.69/aom/health", "GET"),
/*
    // 🔍 DİĞER MODÜLLER - Test edilecek
    NOTIFICATION_SERVICE("Notification Service", "http://34.141.21.67:8080/notification/health", "GET"),
*/;
    private final String displayName;
    private final String healthEndpoint;
    private final String httpMethod;

    ModuleHealth(String displayName, String healthEndpoint, String httpMethod) {
        this.displayName = displayName;
        this.healthEndpoint = healthEndpoint;
        this.httpMethod = httpMethod;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getHealthEndpoint() {
        return healthEndpoint;
    }

    public String getHttpMethod() {
        return httpMethod;
    }
}