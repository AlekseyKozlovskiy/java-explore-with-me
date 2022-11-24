package ru.practicum.explore.user.dto;

import ru.practicum.explore.user.User;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
