package ru.practicum.explore.compilations;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.compilations.dto.CompilationDto;
import ru.practicum.explore.compilations.dto.NewDto;

import java.util.List;

public interface CompilationService {
    NewDto createCompilation(CompilationDto compilationDto);

    NewDto get(Long compId);

    List<NewDto> get(Boolean pinned, PageRequest pageRequest);

    NewDto addEventToCompilation(Long compId, Long eventId);

    NewDto changePinned(Long compId);

    void delete(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);
}
