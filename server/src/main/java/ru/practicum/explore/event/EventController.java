package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.event.dto.UpdateEventDto;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
public class EventController {
    private final EventService eventService;

    @PatchMapping
    ResponseEntity<EventFullDto> update(@RequestBody UpdateEventDto updateEventDto,
                                        @PathVariable Long userId) {
        return ResponseEntity.ok(eventService.update(updateEventDto, userId));
    }

    @PostMapping
    ResponseEntity<EventFullDto> add(@RequestBody EventNewDto eventNewDto,
                                     @PathVariable(value = "userId") long userId) {
        log.info("EWM-Server: Add new event {}", eventNewDto);
        return ResponseEntity.ok(eventService.add(eventNewDto, userId));
    }

    @GetMapping
    ResponseEntity<List<EventFullDto>> get(@PathVariable(value = "userId") Long userId,
                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(eventService.get(userId, pageRequest));
    }


}