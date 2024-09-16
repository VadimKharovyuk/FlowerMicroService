package com.example.flovermodel.dto;

import com.example.flovermodel.model.JobPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationDTO {
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String description ;

    // Используем тот же enum для должности
    private JobPosition jobPosition;
}
