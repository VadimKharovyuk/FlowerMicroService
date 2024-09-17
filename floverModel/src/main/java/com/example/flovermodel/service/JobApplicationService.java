package com.example.flovermodel.service;

import com.example.flovermodel.dto.JobApplicationDTO;
import com.example.flovermodel.mapper.JobApplicationMapper;
import com.example.flovermodel.model.JobApplication;
import com.example.flovermodel.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {
    private final JobApplicationRepository jobApplicationRepository;

    public JobApplicationDTO create(JobApplicationDTO jobApplicationDTO) {
        // Преобразуем DTO в сущность
        JobApplication jobApplication = JobApplicationMapper.toEntity(jobApplicationDTO);

        // Сохраняем сущность в базе данных
        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);

        // Преобразуем сохранённую сущность обратно в DTO
        return JobApplicationMapper.toDto(savedJobApplication);
    }

    public List<JobApplicationDTO> list() {
        return jobApplicationRepository.findAll()
                .stream()
                .map(JobApplicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public JobApplicationDTO findById(Long id) {
        return jobApplicationRepository.findById(id)
                .map(JobApplicationMapper::toDto) // Преобразование в DTO, если сущность найдена
                .orElseThrow(() -> new RuntimeException("Job application not found with id: " + id));
    }
}