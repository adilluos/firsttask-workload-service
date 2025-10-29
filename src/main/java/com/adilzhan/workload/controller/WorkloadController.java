package com.adilzhan.workload.controller;

import com.adilzhan.workload.model.TrainerWorkload;
import com.adilzhan.workload.model.WorkloadRequest;
import com.adilzhan.workload.service.WorkloadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/workload")
public class WorkloadController {
    private final WorkloadService workloadService;

    public WorkloadController(WorkloadService workloadService) {
        this.workloadService = workloadService;
    }

    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateWorkload(@RequestBody WorkloadRequest req) {
        workloadService.updateWorkload(req);
        System.out.println("Workload updated SUCCESSFULLY");
    }

    @GetMapping("/{username}/{year}/{month}")
    public int getMonthlyWorkload(@PathVariable String username,
                                  @PathVariable int year,
                                  @PathVariable int month) {
        return workloadService.getMonthlyWorkload(username, year, month);
    }

    @GetMapping
    public Map<String, TrainerWorkload> getAllWorkloads() {
        return workloadService.getAll();
    }
}
