package ru.practicum.explore.compilations.dto;

import lombok.Data;
import ru.practicum.explore.event.Event;

import java.util.List;

@Data
public class NewDto {
    Long id;
    private boolean pinned;
    private String title;
    private List<Event> events;
}
