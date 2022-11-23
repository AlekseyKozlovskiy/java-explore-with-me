package ru.practicum.explore.client;

import lombok.Data;

@Data
public class EndpointHitDto {
    String app;
    String uri;
    String ip;
    String timestamp;
}