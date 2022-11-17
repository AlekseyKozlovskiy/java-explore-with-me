package ru.practicum.explore.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.compilations.dto.CompilationDto;
import ru.practicum.explore.compilations.dto.NewDto;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.exceptions.IncorrectRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventRepository eventRepository;

    @Override
    public NewDto createCompilation(CompilationDto compilationDto) {
        System.out.println(compilationDto);
        Set<Event> eventList = compilationDto.getEvents().stream()
                .map((id) -> eventRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
        Compilation compilation = compilationMapper.toNewCompilation(compilationDto);
        compilation.setEvents(eventList);
        return compilationMapper.toNewCompilation(compilationRepository.save(compilation));
    }

    @Override
    public NewDto get(Long compId) {
        return compilationMapper.toNewCompilation(compilationRepository.findById(compId).orElseThrow());
    }

    @Override
    public List<NewDto> get(Boolean pinned, PageRequest pageRequest) {
        if (pinned != null) {
            List<Compilation> compilations = compilationRepository.getAllByPinned(pinned, pageRequest);
            return compilations.stream().map(compilationMapper::toNewCompilation).collect(Collectors.toList());
        } else {
            return compilationRepository.findAll().stream().map(compilationMapper::toNewCompilation).collect(Collectors.toList());
        }
    }

    @Override
    public NewDto addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        compilation.getEvents().add(eventRepository.findById(eventId).orElseThrow());
        return compilationMapper.toNewCompilation(compilationRepository.save(compilation));
    }

    @Override
    public NewDto changePinned(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        compilation.setPinned(!compilation.isPinned());
        return compilationMapper.toNewCompilation(compilationRepository.save(compilation));
    }

    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId).orElseThrow(() ->
                new IncorrectRequest("подборка не найдена"));
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        Event deletedEvent = eventRepository.findById(eventId).orElseThrow();
        compilation.getEvents().removeIf(e -> e.getId().equals(eventId));
        compilationRepository.save(compilation);
    }
}