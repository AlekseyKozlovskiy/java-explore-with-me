package ru.practicum.explore.user;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.participation.dto.ParticipationDto;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto add(UserDto userDto);

    List<UserDto> get(List<Long> ids, PageRequest pageRequest);

    EventFullDto getEventByUser(Long eventId, Long userId);

    EventFullDto setRejectByUser(Long eventId, Long userId);

    List<ParticipationDto> getUserRequest(Long userId);

    List<ParticipationDto> getParticipationRequestsOfUser(Long userId, Long eventId);

    ParticipationDto cancelRequest(Long userId, Long requestId);

    ParticipationDto rejectRequest(long userId, long eventId, long requestId);

    ParticipationDto confirmRequest(long userId, long eventId, long requestId);

    void delete(Long userId);

}
