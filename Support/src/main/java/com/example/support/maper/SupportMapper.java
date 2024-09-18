package com.example.support.maper;

import com.example.support.dto.SupportDto;
import com.example.support.model.Support;

public class SupportMapper {

    public static SupportDto toDto(Support support) {
        if (support == null) {
            return null;
        }

        return SupportDto.builder()
                .id(support.getId())
                .topic(support.getTopic())
                .messages(support.getMessages())
                .email(support.getEmail())
                .build();
    }

    public static Support toEntity(SupportDto supportDto) {
        if (supportDto == null) {
            return null;
        }

        Support support = new Support();
        support.setId(supportDto.getId());
        support.setTopic(supportDto.getTopic());
        support.setMessages(supportDto.getMessages());
        support.setEmail(supportDto.getEmail());

        return support;
    }
}