package ru.practicum.explore.event.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EventNewDto {
    @NotBlank
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private Location location;
    private boolean paid;
    private Long participantLimit;
    private boolean requestModeration;
    @NotBlank
    private String title;
    private Boolean availabilityOfComments = true;
}
