package com.adilzhan.workload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerWorkload {
    private String username;
    private String firstName;
    private String lastName;
    private boolean active;
    private Map<Integer, Map<Integer, Integer>> workload;
}
