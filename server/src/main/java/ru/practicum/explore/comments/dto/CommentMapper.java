package ru.practicum.explore.comments.dto;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import ru.practicum.explore.comments.Comment;
import ru.practicum.explore.utils.FormatterDate;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = CommentMapper.DateMapper.class)
public interface CommentMapper {
    Comment toComment(CommentDto commentDto);

    @Mapping(target = "eventId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    CommentDto toCommentDto(Comment comment);

    @AfterMapping
    default void setEventAdnUser(@MappingTarget CommentDto commentDto, Comment comment) {
        commentDto.setUserId(comment.getUser().getId());
        commentDto.setEventId(comment.getEvent().getId());
    }

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


