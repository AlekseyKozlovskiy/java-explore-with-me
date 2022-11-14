package ru.practicum.explore.participation.dto;

import lombok.Data;
import ru.practicum.explore.event.State;

import java.time.LocalDateTime;

@Data
public class ParticipationDto {
    Long id;
    LocalDateTime created;

    Long eventId;

    Long requester;

    State status;
}