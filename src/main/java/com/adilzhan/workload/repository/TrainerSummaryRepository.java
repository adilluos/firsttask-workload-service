package com.adilzhan.workload.repository;

import com.adilzhan.workload.model.TrainerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TrainerSummaryRepository extends MongoRepository<TrainerSummary, String> {
    Optional<TrainerSummary> findByTrainerUsername(String username);
}
