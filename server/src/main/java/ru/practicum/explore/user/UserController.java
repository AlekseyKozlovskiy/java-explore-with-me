package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.participation.dto.ParticipationDto;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/admin/users")
    ResponseEntity<UserDto> add(@Validated @RequestBody UserDto userDto) {
        log.info("EWM-Server: Add new user {}", userDto);
        return ResponseEntity.ok(userService.add(userDto));
    }

    @GetMapping("/admin/users")
    ResponseEntity<List<UserDto>> get(@RequestParam(name = "ids") List<Long> ids,
                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.get(ids, pageRequest));
    }

    @DeleteMapping("/admin/users/{userId}")
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    ResponseEntity<EventFullDto> getEventByUser(@PathVariable Long eventId,
                                                @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getEventByUser(eventId, userId));
    }

    @GetMapping("/users/{userId}/requests")
    ResponseEntity<List<ParticipationDto>> getUserRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserRequest(userId));
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    ResponseEntity<List<ParticipationDto>> getParticipationRequestsOfUser(@PathVariable Long userId,
                                                                          @PathVariable Long eventId) {
        return ResponseEntity.ok(userService.getParticipationRequestsOfUser(userId, eventId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    ResponseEntity<EventFullDto> setRejectByUser(@PathVariable Long eventId,
                                                 @PathVariable Long userId) {
        System.out.println(eventId + " " + userId);
        return ResponseEntity.ok(userService.setRejectByUser(eventId, userId));
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationDto cancelRequest(@PathVariable Long userId, @PathVariable Long requestId) {
        return userService.cancelRequest(userId, requestId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationDto rejectRequest(@PathVariable(value = "userId") long userId,
                                          @PathVariable(value = "eventId") long eventId,
                                          @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} reject request={} for event={}", userId, requestId, eventId);
        return userService.rejectRequest(userId, eventId, requestId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationDto confirmRequest(@PathVariable(value = "userId") long userId,
                                           @PathVariable(value = "eventId") long eventId,
                                           @PathVariable(value = "reqId") long requestId) {
        log.info("PRIVATE: User={} confirm request={} for event={}", userId, requestId, eventId);
        return userService.confirmRequest(userId, eventId, requestId);
    }

}
