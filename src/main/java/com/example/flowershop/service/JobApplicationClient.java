package com.example.flowershop.service;

import com.example.flowershop.dto.JobApplicationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobApplicationClient {

    private final RestTemplate restTemplate;

    @Value("${job.service.url}")
    private String jobClientUrl;

    // Метод для создания новой анкеты

    public JobApplicationDTO createJob(JobApplicationDTO jobApplicationDTO) {
        String url = jobClientUrl + "/job";
        return restTemplate.postForObject(url, jobApplicationDTO, JobApplicationDTO.class);
    }

    // Метод для получения всех анкет
    public List<JobApplicationDTO> findAll() {
        String url = jobClientUrl + "/job/all";
        ResponseEntity<List<JobApplicationDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<JobApplicationDTO>>() {}
        );
        return response.getBody();
    }

    // Метод для получения анкеты по ID
    public JobApplicationDTO findById(Long id) {
        String url = jobClientUrl + "/job/" + id;
        return restTemplate.getForObject(url, JobApplicationDTO.class);
    }
}