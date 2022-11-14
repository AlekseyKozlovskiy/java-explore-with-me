package ru.practicum.explore.event.dto;

import lombok.Data;
import ru.practicum.explore.event.dto.Location;

@Data
public class EventNewDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private String title;

}
