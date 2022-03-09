package com.barber.servicewebsocket.web.model;

import lombok.Data;

@Data
public class PingPongDTO {
    private String name;
    private String address;
    private String host;
    private String username;
    private String tokenIntegration;
    private String os;
    private String clientId;
    private String idExecutions;
    private Integer reconnect;
    private Double physicalMemory;
    private Double usedMemory;
    private Double currentProcessMemory;
    private Double usedCpu;
    private Double currentProcessCpu;
    private String taskStatus;
}