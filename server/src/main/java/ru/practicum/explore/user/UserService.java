package ru.practicum.explore.user;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);
    List<UserDto> get(List<Long> ids, PageRequest pageRequest);
    void delete(Long userId);
}
