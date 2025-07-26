package com.example.healthcheck;

public class HealthResponse {
    private int status;
    private String service;
    private double cpu_percent;
    private double memory_percent;
    private double disk_percent;
    private String jar_status;
    private String uptime;
    private String timestamp;

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public double getCpu_percent() { return cpu_percent; }
    public void setCpu_percent(double cpu_percent) { this.cpu_percent = cpu_percent; }

    public double getMemory_percent() { return memory_percent; }
    public void setMemory_percent(double memory_percent) { this.memory_percent = memory_percent; }

    public double getDisk_percent() { return disk_percent; }
    public void setDisk_percent(double disk_percent) { this.disk_percent = disk_percent; }

    public String getJar_status() { return jar_status; }
    public void setJar_status(String jar_status) { this.jar_status = jar_status; }

    public String getUptime() { return uptime; }
    public void setUptime(String uptime) { this.uptime = uptime; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}