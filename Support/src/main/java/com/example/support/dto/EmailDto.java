package com.example.support.dto;

import lombok.Data;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String text;
}
