package com.example.support.service;

import com.example.support.dto.SupportDto;
import com.example.support.maper.SupportMapper;
import com.example.support.model.Support;
import com.example.support.repository.SupportRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;



    public SupportDto create(SupportDto supportDto) {
        // Преобразуем DTO в сущность
        Support support = SupportMapper.toEntity(supportDto);

        // Сохраняем сущность и получаем результат
        Support savedSupport = supportRepository.save(support);
        return SupportMapper.toDto(savedSupport);
    }

    public List<SupportDto> dtoList (){
       return supportRepository.findAll().stream()
                .map(SupportMapper::toDto)
                .collect(Collectors.toList());
    }
    public SupportDto getById(Long id) {
        Support support = supportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Support entry not found with id: " + id));
        return SupportMapper.toDto(support);
    }


}
