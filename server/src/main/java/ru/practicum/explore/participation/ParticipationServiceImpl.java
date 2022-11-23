package ru.practicum.explore.participation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.State;
import ru.practicum.explore.compilations.exceptions.IncorrectRequest;
import ru.practicum.explore.participation.dto.ParticipationDto;
import ru.practicum.explore.participation.dto.ParticipationMapper;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {
    private final ParticipationRepository participationRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final ParticipationMapper participationMapper;

    @Override
    public ParticipationDto add(Long userId, Long eventId) {

        Participation request = participationRepository.findByRequesterIdAndEventId(userId, eventId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new IncorrectRequest("Event not found!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IncorrectRequest("User not found!"));
        if (request != null) {
            throw new IncorrectRequest("Request already exists!");
        }
        Participation participation = new Participation();
        participation.setRequester(user);
        participation.setCreated(LocalDateTime.now());
        participation.setStatus(State.PENDING);
        participation.setEvent(event);

        Participation saveRequest = participationRepository.save(participation);
        return participationMapper.toParticipationDto(saveRequest);
    }
}
