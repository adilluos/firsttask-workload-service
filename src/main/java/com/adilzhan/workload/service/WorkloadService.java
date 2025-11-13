package com.adilzhan.workload.service;

import com.adilzhan.workload.model.TrainerSummary;
import com.adilzhan.workload.model.TrainerWorkload;
import com.adilzhan.workload.model.WorkloadRequest;
import com.adilzhan.workload.repository.TrainerSummaryRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WorkloadService {

    private final Map<String, TrainerWorkload> workloads = new ConcurrentHashMap<>();

    private final TrainerSummaryRepository trainerSummaryRepository;

    public WorkloadService(TrainerSummaryRepository trainerSummaryRepository) {
        this.trainerSummaryRepository = trainerSummaryRepository;
    }

    public void updateWorkload(WorkloadRequest req) {
        TrainerSummary summary = trainerSummaryRepository.findByTrainerUsername(req.getTrainerUsername())
                .orElseGet(() -> {
                    TrainerSummary s = new TrainerSummary();
                    s.setTrainerUsername(req.getTrainerUsername());
                    s.setFirstName(req.getTrainerFirstName());
                    s.setLastName(req.getTrainerLastName());
                    s.setActive(req.isActive());
                    return s;
                });

        int year = req.getTrainingDate().getYear();
        int month = req.getTrainingDate().getMonthValue();

        summary.getWorkload()
                .computeIfAbsent(year, y -> new HashMap<>())
                .merge(month, req.getTrainingDuration(), Integer::sum);

        trainerSummaryRepository.save(summary);
        System.out.println("Updated workload for " + req.getTrainerUsername());
    }

//    public void updateWorkload(WorkloadRequest req) {
//        TrainerWorkload wl = workloads.computeIfAbsent(
//                req.getTrainerUsername(),
//                u -> new TrainerWorkload(u, req.getTrainerFirstName(),
//                        req.getTrainerLastName(), req.isActive(), new HashMap<>())
//        );
//
//        int year = req.getTrainingDate().getYear();
//        int month = req.getTrainingDate().getMonthValue();
//
//        wl.getWorkload()
//                .computeIfAbsent(year, y -> new HashMap<>())
//                .merge(month,
//                        req.getActionType().equalsIgnoreCase("ADD")
//                                ? req.getTrainingDuration()
//                                : -req.getTrainingDuration(),
//                        Integer::sum);
//        System.out.println(wl);
//    }
//
//    public int getMonthlyWorkload(String username, int year, int month) {
//        TrainerWorkload wl = workloads.get(username);
//        if (wl == null) return 0;
//        return wl.getWorkload()
//                .getOrDefault(year, Collections.emptyMap())
//                .getOrDefault(month, 0);
//    }
//
//    public Map<String, TrainerWorkload> getAll() {
//        return workloads;
//    }
}
