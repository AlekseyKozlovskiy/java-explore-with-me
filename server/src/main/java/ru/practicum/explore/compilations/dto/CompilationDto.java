package ru.practicum.explore.compilations.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explore.event.dto.EventShortDto;

import javax.validation.Valid;
import java.util.List;

@Builder
@Valid
@Setter
@Getter
public class CompilationDto {
    private Long id;
    private List<EventShortDto> eventShortDtos;
    private boolean pinned;
    private String title;

}
