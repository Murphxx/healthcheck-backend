package com.example.healthcheck;

public class AOMResponse {
    private String timestamp;
    private int status;
    private String jar_status;
    private String service;
    private double disk_percent;
    private double memory_percent;
    private double cpu_percent;
    private String uptime;
    private String error;
    private int oracle_health;
    private int hazelcast_health;
    private int voltdb_health;
    private int fps_health;

    // Getters and Setters
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getJar_status() { return jar_status; }
    public void setJar_status(String jar_status) { this.jar_status = jar_status; }

    public String getService() { return service; }
    public void setService(String service) { this.service = service; }

    public double getDisk_percent() { return disk_percent; }
    public void setDisk_percent(double disk_percent) { this.disk_percent = disk_percent; }

    public double getMemory_percent() { return memory_percent; }
    public void setMemory_percent(double memory_percent) { this.memory_percent = memory_percent; }

    public double getCpu_percent() { return cpu_percent; }
    public void setCpu_percent(double cpu_percent) { this.cpu_percent = cpu_percent; }

    public String getUptime() { return uptime; }
    public void setUptime(String uptime) { this.uptime = uptime; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public int getOracle_health() { return oracle_health; }
    public void setOracle_health(int oracle_health) { this.oracle_health = oracle_health; }

    public int getHazelcast_health() { return hazelcast_health; }
    public void setHazelcast_health(int hazelcast_health) { this.hazelcast_health = hazelcast_health; }

    public int getVoltdb_health() { return voltdb_health; }
    public void setVoltdb_health(int voltdb_health) { this.voltdb_health = voltdb_health; }

    public int getFps_health() { return fps_health; }
    public void setFps_health(int fps_health) { this.fps_health = fps_health; }
} 