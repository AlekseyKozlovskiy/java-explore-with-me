package ru.practicum.explore.event;

import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventNewDto;

public interface EventService {
    EventFullDto add(EventNewDto eventNewDto, Long userId);
}
