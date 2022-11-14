package ru.practicum.explore.event.dto;

import lombok.*;
import ru.practicum.explore.categories.CategoryDto;
import ru.practicum.explore.event.State;
import ru.practicum.explore.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    private Integer id;

    private String annotation;

    private CategoryDto category;

    private Boolean confirmedRequests;

    private LocalDateTime createdOn;

    private String description;

    private String eventDate;

    private Boolean paid;

    private UserDto initiator;

    private Long participantLimit;

    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private String title;

    private Long views;

    private Location location;

}
