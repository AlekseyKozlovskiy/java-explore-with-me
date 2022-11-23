package ru.practicum.explore.participation.dto;

import lombok.Data;
import ru.practicum.explore.event.State;

import java.time.LocalDateTime;

@Data
public class ParticipationDto {
    private Long id;
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private State status;
}