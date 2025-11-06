package com.adilzhan.workload.listener;

import com.adilzhan.workload.config.ActiveMqConfig;
import com.adilzhan.workload.model.WorkloadRequest;
import com.adilzhan.workload.service.WorkloadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class WorkloadMessageListener {
    private final WorkloadService workloadService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WorkloadMessageListener(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @JmsListener(destination = ActiveMqConfig.WORKLOAD_QUEUE)
    public void receiveMessage(String messageJson) {
        try {
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            WorkloadRequest req = mapper.readValue(messageJson, WorkloadRequest.class);
            workloadService.updateWorkload(req);
            System.out.println("Message consumed: " + req);
        } catch (Exception e) {
            System.err.println("Failed to process message: " + e.getMessage());
        }
    }
}
