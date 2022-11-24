package ru.practicum.stats;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.practicum.utils.FormatterDate;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = EndpointMapper.DateMapper.class)
public interface EndpointMapper {

    EndpointHit toEndpoint(EndpointHitDto endpointHitDto);

    @Component
    class DateMapper {
        public String asString(LocalDateTime date) {
            return date.format(FormatterDate.formatter());
        }

        public LocalDateTime asDate(String date) {
            return LocalDateTime.parse(date, FormatterDate.formatter());
        }
    }
}
