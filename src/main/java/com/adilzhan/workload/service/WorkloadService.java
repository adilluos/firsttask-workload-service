//package com.adilzhan.workload.service;
//
//import com.adilzhan.workload.dto.WorkloadRequest;
//import com.adilzhan.workload.model.TrainerSummary;
//import com.adilzhan.workload.repository.TrainerSummaryRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class WorkloadService {
//
////    private final Map<String, TrainerWorkload> workloads = new ConcurrentHashMap<>();
//
//    private final TrainerSummaryRepository trainerSummaryRepository;
//
//    public WorkloadService(TrainerSummaryRepository trainerSummaryRepository) {
//        this.trainerSummaryRepository = trainerSummaryRepository;
//    }
//
//    public void updateWorkload(WorkloadRequest req) {
//        TrainerSummary summary = trainerSummaryRepository.findByTrainerUsername(req.getTrainerUsername())
//                .orElseGet(() -> {
//                    TrainerSummary s = new TrainerSummary();
//                    s.setTrainerUsername(req.getTrainerUsername());
//                    s.setFirstName(req.getTrainerFirstName());
//                    s.setLastName(req.getTrainerLastName());
//                    s.setActive(req.isActive());
//                    return s;
//                });
//
//        int year = req.getTrainingDate().getYear();
//        int month = req.getTrainingDate().getMonthValue();
//
//        summary.getWorkload()
//                .computeIfAbsent(year, y -> new HashMap<>())
//                .merge(month, req.getTrainingDuration(), Integer::sum);
//
//        trainerSummaryRepository.save(summary);
//        System.out.println("Updated workload for " + req.getTrainerUsername());
//    }
//
//    public int getMonthlyWorkload(String username, int year, int month) {
//        Optional<TrainerSummary> wl = trainerSummaryRepository.findByTrainerUsername(username);
//        if (wl.isEmpty()) return 0;
//        return wl.get().getWorkload()
//                .getOrDefault(year, Collections.emptyMap())
//                .getOrDefault(month, 0);
//    }
//
//    public Map<String, TrainerSummary> getAll() {
//        List<TrainerSummary> allTrainings = trainerSummaryRepository.findAll();
//        Map<String, TrainerSummary> allWorkloads = allTrainings.stream().collect(Collectors.toMap(TrainerSummary::getTrainerUsername, w -> w));
//        return allWorkloads;
//    }
//}
