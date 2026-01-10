package com.adilzhan.workload.service;

import com.adilzhan.workload.dto.WorkloadRequest;

public interface WorkloadUpdateHandler {
    void handle(WorkloadRequest req);
}
