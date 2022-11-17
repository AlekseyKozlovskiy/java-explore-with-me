package ru.practicum.explore.compilations;

import org.mapstruct.*;
import ru.practicum.explore.compilations.dto.CompilationDto;
import ru.practicum.explore.compilations.dto.NewDto;
import ru.practicum.explore.event.Event;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompilationMapper {

    @Mapping(target = "events", ignore = true)
    CompilationDto toCompilationDto(Compilation compilation);

    @AfterMapping
    default void setEvents(@MappingTarget CompilationDto compilationDto, Compilation compilation) {
        Set<Long> longs = compilation.getEvents().stream().map(Event::getId).collect(Collectors.toSet());
        compilationDto.setEvents(longs);
    }

    @Mapping(target = "events", ignore = true)
    Compilation toNewCompilation(CompilationDto compilationDto);

    NewDto toNewCompilation(Compilation compilation);


}