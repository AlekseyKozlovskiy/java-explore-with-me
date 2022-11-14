package ru.practicum.explore.event.dto;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.practicum.explore.event.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring", uses = EventMapper.DateMapper.class)
public interface EventMapper {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Event toEventFromFullDto(EventFullDto eventFullDto);

    EventFullDto toEventFullDto(Event event);

    @AfterMapping
    default void setLocation(@MappingTarget EventFullDto eventFullDto, Event event) {
        eventFullDto.setLocation(new Location(event.getLat(), event.getLon()));
    }

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "lat", ignore = true)
    @Mapping(target = "lon", ignore = true)
    Event toEventFromNewDto(EventNewDto eventNewDto);

    @AfterMapping
    default void setLatLon(@MappingTarget Event event, EventNewDto eventNewDto) {
        event.setLat(eventNewDto.getLocation().getLat());
        event.setLon(eventNewDto.getLocation().getLon());
    }

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
