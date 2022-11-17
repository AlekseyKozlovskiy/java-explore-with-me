package ru.practicum.explore.compilations.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CompilationDto {
    private Long id;
    private Set<Long> events;
    private boolean pinned;
    private String title;
}
