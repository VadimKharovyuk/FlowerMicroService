package com.example.support.dto;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SupportDto {
    private Long id;

    private String topic;
    private String messages;
    private String email;
}
