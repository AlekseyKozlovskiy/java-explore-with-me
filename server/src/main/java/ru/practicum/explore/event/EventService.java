package ru.practicum.explore.event;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.event.dto.UpdateEventDto;

import java.util.List;

public interface EventService {
    EventFullDto add(EventNewDto eventNewDto, Long userId);

    EventFullDto update(UpdateEventDto updateEventDto, Long userId);

    List<EventFullDto> get(Long userId, PageRequest pageRequest);
}
