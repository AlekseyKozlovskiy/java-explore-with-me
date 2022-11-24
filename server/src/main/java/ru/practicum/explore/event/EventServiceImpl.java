package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.Category;
import ru.practicum.explore.categories.CategoryRepository;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.event.dto.UpdateEventDto;
import ru.practicum.explore.exceptions.IncorrectRequest;
import ru.practicum.explore.user.UserRepository;
import ru.practicum.explore.utils.FormatterDate;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public EventFullDto add(EventNewDto eventNewDto, Long userId) {
        Event event = eventMapper.toEventFromNewDto(eventNewDto);
        event.setCategory(categoryRepository.findById(eventNewDto.getCategory()).orElseThrow());
        event.setInitiator(userRepository.findById(userId).orElseThrow());
        event.setState(State.PENDING);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
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

    @Override
    public EventFullDto getEventById(Long eventId) {
        return eventMapper.toEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new IncorrectRequest("Событие не найдено " + eventId)));
    }

    @Override
    @Transactional
    public EventFullDto publishedEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IncorrectRequest("Событие не найдено " + eventId));
        event.setPublishedOn(LocalDateTime.now().plusHours(2));
        event.setState(State.PUBLISHED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    public static Event toUpdatedEvent(EventNewDto eventNewDto, Category category, Event event) {
        if (eventNewDto.getAnnotation() != null) {
            event.setAnnotation(eventNewDto.getAnnotation());
        }
        if (category != null) {
            event.setCategory(category);
        }
        if (eventNewDto.getDescription() != null) {
            event.setDescription(eventNewDto.getDescription());
        }
        if (eventNewDto.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(eventNewDto.getEventDate(), FormatterDate.formatter()));
        }
        event.setPaid(eventNewDto.isPaid());
        event.setParticipantLimit(eventNewDto.getParticipantLimit());
        event.setRequestModeration(eventNewDto.isRequestModeration());
        if (eventNewDto.getTitle() != null) {
            event.setTitle(eventNewDto.getTitle());
        }
        if (eventNewDto.getLocation() != null) {
            event.setLat(eventNewDto.getLocation().getLat());
            event.setLon(eventNewDto.getLocation().getLon());
        }
        return event;
    }

    @Override
    public List<EventFullDto> getEventsByParams(List<Long> users, List<State> states,
                                                List<Long> categories, String rangeStart,
                                                String rangeEnd, PageRequest pageRequest) {
        Specification<Event> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (users != null && !users.isEmpty()) {
                predicates.add(builder.and(root.get("initiator").in(users)));
            }
            if (states != null && !states.isEmpty()) {
                predicates.add(builder.and(root.get("state").in(states)));
            }
            if (categories != null && !categories.isEmpty()) {
                predicates.add(builder.and(root.get("category").in(categories)));
            }
            if ((rangeStart != null && rangeEnd != null)) {
                predicates.add(builder.greaterThan(root.get("eventDate"), LocalDateTime.parse(rangeStart, FormatterDate.formatter())));
                predicates.add(builder.lessThan(root.get("eventDate"), LocalDateTime.parse(rangeEnd, FormatterDate.formatter())));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };

        Page<Event> events = eventRepository.findAll(specification, pageRequest);
        return events.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto setReject(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setState(State.CANCELED);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto editEvent(Long eventId, EventNewDto eventNewDto) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        Category category = categoryRepository.findById(eventNewDto.getCategory()).orElseThrow();
        Event updated = eventRepository.save(toUpdatedEvent(eventNewDto, category, event));
        return eventMapper.toEventFullDto(updated);
    }

    @Override
    public List<EventFullDto> getEvents(String text, List<Long> categories,
                                        Boolean paid, String rangeStart,
                                        String rangeEnd, Boolean onlyAvailable,
                                        PageRequest pageRequest) {

        Specification<Event> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("state"), State.PUBLISHED));
            if (text != null && !text.isEmpty()) {
                predicates.add(builder.or(builder.like(builder.lower(root.get("annotation")), "%" + text.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("description")), "%" + text.toLowerCase() + "%")));
            }
            if (categories != null) {
                predicates.add(builder.and(root.get("category").in(categories)));
            }
            if (paid != null) {
                predicates.add(builder.equal(root.get("paid"), paid));
            }
            if ((rangeStart != null && rangeEnd != null)) {
                predicates.add(builder.greaterThan(root.get("eventDate"), LocalDateTime.parse(rangeStart, FormatterDate.formatter())));
                predicates.add(builder.lessThan(root.get("eventDate"), LocalDateTime.parse(rangeEnd, FormatterDate.formatter())));
            }
            if (onlyAvailable) {
                predicates.add(builder.or(builder.equal(root.get("participantLimit"), 0),
                        builder.and(builder.notEqual(root.get("participantLimit"), 0),
                                builder.greaterThan(root.get("participantLimit"), root.get("confirmedRequests")))));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
        Page<Event> events = eventRepository.findAll(specification, pageRequest);

        return events.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }
}
