package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.CategoryRepository;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.user.dto.Mapper;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public UserDto add(UserDto userDto) {
        return mapper.toUserDto(userRepository.save(mapper.toUser(userDto)));
    }

    @Override
    public List<UserDto> get(List<Long> ids, PageRequest pageRequest) {
        List<User> usersByIdIn = userRepository.findUsersByIdIn(ids, pageRequest);
        return usersByIdIn.stream().map(mapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
