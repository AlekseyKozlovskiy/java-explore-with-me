package ru.practicum.explore.comments;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.comments.dto.CommentDto;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.user.dto.UserShortDto;

import java.util.List;

public interface CommentService {
    CommentDto add(Long userId, Long eventId, CommentDto commentDto);

    void delete(Long commentId);

    void delete(Long userId, Long commentId);

    UserShortDto ban(Long userId);

    EventFullDto availableComment(Long eventId);

    List<CommentDto> getCommentByEventId(PageRequest pageRequest, Long eventId, Long userId);

}
