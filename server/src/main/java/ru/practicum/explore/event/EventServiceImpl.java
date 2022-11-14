package ru.practicum.explore.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.CategoryRepository;
import ru.practicum.explore.event.dto.EventFullDto;
//import ru.practicum.explore.user.UserMapper;/
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.event.dto.EventNewDto;
import ru.practicum.explore.user.UserRepository;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto add(EventNewDto eventNewDto, Long userId) {
//        System.out.println(eventNewDto);
        Event event = eventMapper.toEventFromNewDto(eventNewDto);
        event.setCategory(categoryRepository.findById(eventNewDto.getCategory()).orElseThrow());
        event.setInitiator(userRepository.findById(userId).orElseThrow());

        System.out.println("__________________" + eventMapper.toEventFromNewDto(eventNewDto));
        EventFullDto eventFullDto = eventMapper.toEventFullDto(eventRepository.save(event));
        System.out.println("+++++++++++++++" + eventFullDto);

//        event.setLat(eventNewDto.getLocation().getLat());
//        event.setLon(eventNewDto.getLocation().getLon());

//        eventRepository.save(event);
//
//
//
//        EventFullDto eventFullDto = eventMapper.toEventFullDto(event);
//        eventFullDto.setLocation(new Location(event.lat, event.lon));

        return eventMapper.toEventFullDto(eventRepository.save(event));
    }
}
