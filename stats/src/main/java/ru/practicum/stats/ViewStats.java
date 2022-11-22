package ru.practicum.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStats {
    String app;
    String uri;
    Integer hits;
}