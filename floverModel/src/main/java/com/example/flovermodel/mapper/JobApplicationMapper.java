package com.example.flovermodel.mapper;

import com.example.flovermodel.dto.JobApplicationDTO;
import com.example.flovermodel.model.JobApplication;
import com.example.flovermodel.model.JobPosition;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JobApplicationMapper {

    public JobApplicationDTO toDto(JobApplication jobApplication) {
        return JobApplicationDTO.builder()
                .id(jobApplication.getId())
                .email(jobApplication.getEmail())
                .firstName(jobApplication.getFirstName())
                .lastName(jobApplication.getLastName())
                .phoneNumber(jobApplication.getPhoneNumber())
                .description(jobApplication.getDescription())
                // Преобразуем enum в строку
                .jobPosition(JobPosition.valueOf(jobApplication.getJobPosition().name()))
                .build();
    }

    public JobApplication toEntity(JobApplicationDTO jobApplicationDTO) {
        return new JobApplication(
                jobApplicationDTO.getId(),
                jobApplicationDTO.getFirstName(),
                jobApplicationDTO.getLastName(),
                jobApplicationDTO.getPhoneNumber(),
                jobApplicationDTO.getEmail(),
                jobApplicationDTO.getDescription(),
                // Преобразуем строку обратно в enum
                JobPosition.valueOf(String.valueOf(jobApplicationDTO.getJobPosition()))
        );
    }
}