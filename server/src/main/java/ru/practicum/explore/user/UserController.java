package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping
    ResponseEntity<UserDto> add(@RequestBody UserDto userDto) {
        log.info("EWM-Server: Add new user {}", userDto);
        return ResponseEntity.ok(userService.add(userDto));
    }

    @GetMapping
    ResponseEntity<List<UserDto>> get(@RequestParam(name = "ids") List<Long> ids,
                                      @RequestParam(name = "from", defaultValue = "0") Integer from,
                                      @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.get(ids, pageRequest));
    }

    @DeleteMapping("/{userId}")
    void delete(@PathVariable("userId") Long userId) {
        userService.delete(userId);
    }


}
