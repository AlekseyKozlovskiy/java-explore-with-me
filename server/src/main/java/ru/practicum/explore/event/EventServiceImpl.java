package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.CategoryRepository;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.event.dto.UpdateEventDto;
import ru.practicum.explore.user.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto add(EventNewDto eventNewDto, Long userId) {
        Event event = eventMapper.toEventFromNewDto(eventNewDto);
        event.setCategory(categoryRepository.findById(eventNewDto.getCategory()).orElseThrow());
        event.setInitiator(userRepository.findById(userId).orElseThrow());
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto update(UpdateEventDto updateEventDto, Long userId) {
        Event event = eventMapper.toEventFromUpdateEventDto(updateEventDto);
        updateEvent(event, updateEventDto, userId);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> get(Long userId, PageRequest pageRequest) {
        List<Event> eventsById = eventRepository.findAllByInitiatorId(userId, pageRequest);
        return eventsById.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }

    public void updateEvent(Event event, UpdateEventDto updateEventDto, Long userId) {
        event.setCategory(categoryRepository.findById(updateEventDto.getCategory()).orElseThrow());
        event.setState(State.PENDING);
        event.setInitiator(userRepository.findById(userId).orElseThrow());
    }
}
