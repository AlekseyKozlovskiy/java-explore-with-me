package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.event.dto.UpdateEventDto;
import ru.practicum.explore.client.Fein;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class EventController {
    private final EventService eventService;

    private final Fein fein;

    @PatchMapping("/users/{userId}/events")
    ResponseEntity<EventFullDto> update(@Valid @RequestBody UpdateEventDto updateEventDto,
                                        @PathVariable Long userId) {
        log.info("EWM-Server: update event {}", updateEventDto);
        return ResponseEntity.ok(eventService.update(updateEventDto, userId));
    }

    @PostMapping("/users/{userId}/events")
    ResponseEntity<EventFullDto> add(@Valid @RequestBody EventNewDto eventNewDto,
                                     @PathVariable(value = "userId") Long userId) {
        log.info("EWM-Server: Add new event {}", eventNewDto);
        return ResponseEntity.ok(eventService.add(eventNewDto, userId));
    }

    @GetMapping("/users/{userId}/events")
    ResponseEntity<List<EventFullDto>> get(@PathVariable(value = "userId") Long userId,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("EWM-Server: get event {}", userId);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.get(userId, pageRequest));
    }

    @GetMapping("/events/{id}")
    ResponseEntity<EventFullDto> getEventById(@PathVariable("id") Long eventId,
                                              HttpServletRequest request) {
        log.info("EWM-Server: get event by id{}", eventId);
        fein.addToStat(Fein.toEndpointHitDto(request));
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PatchMapping("/admin/events/{eventId}/publish")
    ResponseEntity<EventFullDto> publishedEvent(@PathVariable("eventId") Long eventId) {
        log.info("EWM-Server: published event by id{}", eventId);
        return ResponseEntity.ok(eventService.publishedEvent(eventId));
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getEventsByParams(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @PositiveOrZero @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        log.info("EWM-Server: get events by param: users{}, sttes{}, categories{}", users, states, categories);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return eventService.getEventsByParams(users, states, categories, rangeStart, rangeEnd, pageRequest);
    }

    @PatchMapping("/admin/events/{eventId}/reject")
    ResponseEntity<EventFullDto> setReject(@PathVariable("eventId") Long eventId) {
        log.info("EWM-Server: set Reject event by id{}", eventId);
        return ResponseEntity.ok(eventService.setReject(eventId));
    }

    @PutMapping(path = "/admin/events/{eventId}")
    public EventFullDto editEvent(@PathVariable(value = "eventId") long eventId,
                                  @RequestBody EventNewDto eventNewDto) {
        log.info("EWM-Server: edit Event by id{}, eventDto{}", eventId, eventNewDto);
        return eventService.editEvent(eventId, eventNewDto);
    }

    @GetMapping("/events")
    public List<EventFullDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(required = false, defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false, defaultValue = "") String sort,
            @PositiveOrZero @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @Positive @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            HttpServletRequest request) {
        fein.addToStat(Fein.toEndpointHitDto(request));

        log.info("EWM-Server: get Events");
        int page = from / size;

        Sort sorting = null;
        switch (sort) {
            case "EVENT_DATE":
                sorting = Sort.by(Sort.Direction.DESC, "eventDate");
                break;
            case "VIEWS":
                sorting = Sort.by(Sort.Direction.DESC, "views");
                break;
            default:
                sorting = Sort.by(Sort.Direction.ASC, "id");
        }
        final PageRequest pageRequest = PageRequest.of(page, size, sorting);

        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageRequest);
    }
}