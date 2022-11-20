package ru.practicum.explore.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.CategoryRepository;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.State;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.exceptions.ConflictExceptions;
import ru.practicum.explore.participation.Participation;
import ru.practicum.explore.participation.ParticipationRepository;
import ru.practicum.explore.participation.dto.ParticipationDto;
import ru.practicum.explore.participation.dto.ParticipationMapper;
import ru.practicum.explore.user.dto.Mapper;
import ru.practicum.explore.user.dto.UserDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;

    @Override
    public UserDto add(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName())) {
            throw new ConflictExceptions("имя занято");
        }
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

    @Override
    public EventFullDto getEventByUser(Long eventId, Long userId) {
        return eventMapper.toEventFullDto(eventRepository.findAllByInitiatorIdAndId(userId, eventId));
    }

    @Override
    public List<ParticipationDto> getUserRequest(Long userId) {
        List<Participation> participations = participationRepository.findAllByRequesterId(userId);
        return participations.stream().map(participationMapper::toParticipationDto).collect(Collectors.toList());
    }

    @Override
    public List<ParticipationDto> getParticipationRequestsOfUser(Long userId, Long eventId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!Objects.equals(initiator.getId(), event.getInitiator().getId())) {
            throw new IllegalStateException();
        }
        List<Participation> participations = participationRepository.findByEventId(eventId);
        return participations.stream().map(participationMapper::toParticipationDto).collect(Collectors.toList());
    }

    @Override
    public EventFullDto setRejectByUser(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        System.out.println(event.getInitiator().getId());
        if (!Objects.equals(event.getInitiator().getId(), userId)) {
            throw new IllegalStateException();
        }
        if (!event.getState().equals(State.PENDING)) {
            throw new IllegalStateException();
        }
        event.setState(State.CANCELED);
        Event cancelled = eventRepository.save(event);
        return eventMapper.toEventFullDto(cancelled);
    }

    @Override
    public ParticipationDto cancelRequest(Long userId, Long requestId) {
        Participation participation = participationRepository.findById(requestId).orElseThrow();
        participation.setStatus(State.CANCELED);
        participationRepository.save(participation);
        return participationMapper.toParticipationDto(participation);
    }

    @Override
    public ParticipationDto rejectRequest(long userId, long eventId, long requestId) {
        Participation participation = participationRepository.findById(requestId).orElseThrow();
        participation.setStatus(State.REJECTED);
        Participation rejected = participationRepository.save(participation);
        return participationMapper.toParticipationDto(rejected);
    }

    @Override
    public ParticipationDto confirmRequest(long userId, long eventId, long requestId) {
        User initiator = userRepository.findById(userId).orElseThrow();
        Event event = eventRepository.findById(eventId).orElseThrow();
        if (!Objects.equals(initiator.getId(), event.getInitiator().getId())) {
            throw new IllegalStateException();
        }
        Participation request = participationRepository.findById(requestId).orElseThrow();
        request.setStatus(State.CONFIRMED);
        Participation confirmed = participationRepository.save(request);
        return participationMapper.toParticipationDto(confirmed);
    }
}
