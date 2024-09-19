package com.example.flowershop.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String text;
}
