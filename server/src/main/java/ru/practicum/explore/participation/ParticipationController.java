package ru.practicum.explore.participation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.participation.dto.ParticipationDto;

@RestController()
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class ParticipationController {
    private final ParticipationService participationService;

    @PostMapping("/users/{userId}/requests")
    ResponseEntity<ParticipationDto> add(@PathVariable(value = "userId") Long userId,
                                         @RequestParam(name = "eventId") Long eventId) {
        return ResponseEntity.ok(participationService.add(userId, eventId));
    }
}
