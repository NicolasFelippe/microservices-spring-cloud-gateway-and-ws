package com.barber.servicewebsocket.web.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Computer {


    public String _id;
    public String address;
    public String host;
    public String name;
    public String username;
    public String runtimeVersion;
    public Double progress;
    public String tokenIntegration;
    public String os;

    public String taskStatus;

    public Date lastConnect;
    public String sessionId;

    public String clientId;
    public Integer timeoutGarbageCollector;
    public Integer maxSizeGarbageCollector;
    public Boolean loading;
    public Integer reconnect;
    public String idCurrentExecution;
    public String idExecutions;
    public Integer numberOfExecutions;
    public Integer warningTime;
    public Date lastNotification;
    private Double physicalMemory;
    private Double usedMemory;
    private Double currentProcessMemory;
    private Double usedCpu;
    private Double currentProcessCpu;
    public Boolean startWithManager;
}