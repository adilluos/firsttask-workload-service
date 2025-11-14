package com.adilzhan.workload.service;

import com.adilzhan.workload.model.TrainerWorkload;
import com.adilzhan.workload.model.WorkloadRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WorkloadService {
    private final Map<String, TrainerWorkload> workloads = new ConcurrentHashMap<>();

    public void updateWorkload(WorkloadRequest req) {
        TrainerWorkload wl = workloads.computeIfAbsent(
                req.getTrainerUsername(),
                u -> new TrainerWorkload(u, req.getTrainerFirstName(),
                        req.getTrainerLastName(), req.isActive(), new HashMap<>())
        );

        int year = req.getTrainingDate().getYear();
        int month = req.getTrainingDate().getMonthValue();

        wl.getWorkload()
                .computeIfAbsent(year, y -> new HashMap<>())
                .merge(month,
                        req.getActionType().equalsIgnoreCase("ADD")
                                ? req.getTrainingDuration()
                                : -req.getTrainingDuration(),
                        Integer::sum);
        System.out.println(wl);
    }

    public int getMonthlyWorkload(String username, int year, int month) {
        TrainerWorkload wl = workloads.get(username);
        if (wl == null) return 0;
        return wl.getWorkload()
                .getOrDefault(year, Collections.emptyMap())
                .getOrDefault(month, 0);
    }

    public Map<String, TrainerWorkload> getAll() {
        return workloads;
    }
}
