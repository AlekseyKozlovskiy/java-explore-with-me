package ru.practicum.explore.comments;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.comments.dto.CommentDto;
import ru.practicum.explore.comments.dto.CommentMapper;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.EventRepository;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.event.dto.EventMapper;
import ru.practicum.explore.exceptions.IncorrectRequest;
import ru.practicum.explore.user.User;
import ru.practicum.explore.user.UserRepository;
import ru.practicum.explore.user.dto.Mapper;
import ru.practicum.explore.user.dto.UserShortDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final Mapper userMapper;

    private final EventMapper eventMapper;

    private final EventRepository eventRepository;

    private final CommentMapper commentMapper;

    @Override
    public CommentDto add(Long userId, Long eventId, CommentDto commentDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IncorrectRequest("Пользователь не найден " + userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new IncorrectRequest("Событие не найдено " + userId));
        if (!user.getBannedToComment() && event.getAvailabilityOfComments()) {
            Comment comment = commentMapper.toComment(commentDto);
            comment.setUser(userRepository.findById(userId).orElseThrow());
            comment.setEvent(eventRepository.findById(eventId).orElseThrow());
            return commentMapper.toCommentDto(commentRepository.save(comment));
        } else {
            throw new IncorrectRequest("Пользовател или событие заблокированы для комментариев");
        }
    }

    @Override
    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId).orElseThrow(() ->
                new IncorrectRequest("Комментарий не найден " + commentId));
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void delete(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new IncorrectRequest("Комментарий не найден " + commentId));
        if (comment.getUser().getId().equals(userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IncorrectRequest("Нельзя удалить чужой коммент");
        }
    }

    @Override
    @Transactional
    public UserShortDto ban(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new IncorrectRequest("Пользователь не найден " + userId));
        user.setBannedToComment(!user.getBannedToComment());
        return userMapper.toUserShortDto(userRepository.save(user));
    }

    @Override
    public EventFullDto availableComment(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new IncorrectRequest("Событие не найдено " + eventId));
        event.setAvailabilityOfComments(!event.getAvailabilityOfComments());
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<CommentDto> getCommentByEventId(PageRequest pageRequest, Long eventId, Long userId) {
        List<Comment> returnedList = new ArrayList<>();
        if (eventId != null && userId != null) {
            returnedList = commentRepository.findAllByEventIdAndUserId(eventId, userId, pageRequest);
        } else if (eventId != null) {
            returnedList = commentRepository.findAllByEventId(eventId, pageRequest);
        } else if (userId != null) {
            returnedList = commentRepository.findAllByUserId(userId, pageRequest);
        }
        return returnedList.stream()
                .map(commentMapper::toCommentDto).collect(Collectors.toList());
    }
}
