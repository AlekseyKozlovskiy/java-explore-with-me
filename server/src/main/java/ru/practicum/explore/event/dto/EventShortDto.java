package ru.practicum.explore.event.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explore.categories.dto.CategoryDto;
import ru.practicum.explore.user.dto.UserDto;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Builder
@Valid
@Setter
@Getter
public class EventShortDto {
    private Integer id;

    private String annotation;

    private CategoryDto category;

    private Boolean confirmedRequests;

    private LocalDateTime eventDate;

    private Boolean paid;

    private UserDto initiator;

    private String title;

    private Long views;

    private Location location;
}
