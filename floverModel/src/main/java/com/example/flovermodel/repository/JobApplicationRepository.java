package com.example.flovermodel.repository;

import com.example.flovermodel.model.JobApplication;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication,Long> {
}
