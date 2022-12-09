package ru.practicum.explore.event.dto;

import lombok.Data;

@Data
public class UpdateEventDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private boolean paid;
    private Long participantLimit;
    private String title;
    private Location location;
    private Boolean availabilityOfComments = true;
}
