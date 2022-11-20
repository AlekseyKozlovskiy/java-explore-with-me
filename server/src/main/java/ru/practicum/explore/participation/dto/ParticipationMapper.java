package ru.practicum.explore.participation.dto;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.participation.Participation;

@Mapper(componentModel = "spring", uses = EventMapper.DateMapper.class)
public interface ParticipationMapper {

    @Mapping(target = "event", ignore = true)
    @Mapping(target = "requester", ignore = true)
    ParticipationDto toParticipationDto(Participation participation);

    @AfterMapping
    default void setUserAndEvent(@MappingTarget ParticipationDto participationDto, Participation participation) {
        participationDto.setEvent(participation.getEvent().getId());
        participationDto.setRequester(participation.getRequester().getId());
    }
}
