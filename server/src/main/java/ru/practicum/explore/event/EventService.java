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

    EventFullDto getEventById(Long userId);

    EventFullDto publishedEvent(Long userId);

    EventFullDto setReject(Long eventId);

    List<EventFullDto> getEventsByParams(List<Long> users, List<State> states,
                                         List<Long> categories, String rangeStart,
                                         String rangeEnd, PageRequest pageRequest);

    EventFullDto editEvent(Long eventId, EventNewDto eventNewDto);

    List<EventFullDto> getEvents(String text, List<Long> categories, Boolean paid,
                                 String rangeStart, String rangeEnd, Boolean onlyAvailable, PageRequest pageRequest);
}
