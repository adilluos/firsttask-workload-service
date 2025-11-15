package com.adilzhan.workload.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "trainer_workload_summary")
public class TrainerSummary {
    @Id
    private String id;

    @Indexed(unique = true)
    private String trainerUsername;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;

    private boolean active;

    private Map<Integer, Map<Integer, Integer>> workload = new HashMap<>();
}
