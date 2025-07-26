package com.example.healthcheck;

public enum ModuleHealth {
    // ✅ ÇALIŞAN - Test edildi
    CHF("Charging Function", "http://35.241.210.255:8080/chf/health", "GET"),
    TGF("Traffic Generator", "http://34.44.173.88:5000/TGF/health", "GET"),
    CGF("Charging GW Function", "http://34.51.185.11:8080/CGF/health", "GET"),
    ABMF("Acc & Balance Function", "http://34.52.143.95:8080/ABMF/health", "GET"),
    KAFKA("Kafka", "http://34.38.128.100:9010/kafka/health", "GET");

/*
    // 🔍 DİĞER MODÜLLER - Test edilecek
    SMS_APP("SMS App", "http://34.133.172.73:8080/sms/health", "GET"),
    AOM("Account & Order Mng", "http://34.123.86.69:8080/aom/health", "GET"),
    HAZELCAST("Hazelcast", "http://104.198.77.152:8080/hazelcast/health", "GET"),
    VOLTDB("Volt Active Data", "http://34.10.105.56:8080/voltdb/health", "GET"),
    ORACLE_DB("Oracle Database", "http://34.155.38.208:8080/oracle/health", "GET"),
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