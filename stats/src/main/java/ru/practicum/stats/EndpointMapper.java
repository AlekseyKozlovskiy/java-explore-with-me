package ru.practicum.stats;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = EndpointMapper.DateMapper.class)
public interface EndpointMapper {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    EndpointHitDto toEndpointHitDto(EndpointHit endpointHit);

    EndpointHit toEndpoint(EndpointHitDto endpointHitDto);

    @Component
    class DateMapper {
        public String asString(LocalDateTime date) {
            return date.format(formatter);
        }

        public LocalDateTime asDate(String date) {
            return LocalDateTime.parse(date, formatter);
        }
    }
}
