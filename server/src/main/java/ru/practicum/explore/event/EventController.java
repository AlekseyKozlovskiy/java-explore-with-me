package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventNewDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class EventController {
    private final EventService eventService;

    @PostMapping("/{userId}/events")
    ResponseEntity<EventFullDto> add(@RequestBody EventNewDto eventNewDto,
                                     @PathVariable(value = "userId") long userId) {
        log.info("EWM-Server: Add new event {}", eventNewDto);

        System.out.println(eventNewDto.getDescription() + "!!!!!!!!!!!");
        System.out.println(eventNewDto.getEventDate() + "!!!!!!!!!!!");
        System.out.println(eventNewDto.getCategory() + "!!!!!!!!!!!");
        return ResponseEntity.ok(eventService.add(eventNewDto, userId));
    }
}