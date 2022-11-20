package ru.practicum.explore.participation;

import ru.practicum.explore.participation.dto.ParticipationDto;

public interface ParticipationService {
    ParticipationDto add(Long userId, Long eventId);
}
