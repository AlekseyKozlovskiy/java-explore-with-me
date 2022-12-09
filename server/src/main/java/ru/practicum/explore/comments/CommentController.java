package ru.practicum.explore.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.comments.dto.CommentDto;
import ru.practicum.explore.event.dto.EventFullDto;
import ru.practicum.explore.user.dto.UserShortDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/user/{userId}/event/{eventId}")
    public ResponseEntity<CommentDto> addNewComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable Long userId,
                                                    @PathVariable Long eventId) {
        log.info("EWM-Server: add comment {}", commentDto);
        return ResponseEntity.ok(commentService.add(userId, eventId, commentDto));
    }

    @DeleteMapping("/admin/comment/{commentId}")
    void deleteCommentByAdmin(@PathVariable Long commentId) {
        log.info("EWM-Server: delete comment {}", commentId);
        commentService.delete(commentId);
    }

    @DeleteMapping("/comment/{commentId}/user/{userId}")
    void deleteCommentByUser(@PathVariable Long commentId,
                             @PathVariable Long userId) {
        log.info("EWM-Server: delete comment {} by user {}", commentId, userId);
        commentService.delete(userId, commentId);
    }

    @PatchMapping("admin/{userId}/ban")
    public ResponseEntity<UserShortDto> ban(@PathVariable Long userId) {
        log.info("EWM-Server: Add user to ban user {}", userId);
        return ResponseEntity.ok(commentService.ban(userId));
    }

    @PatchMapping("admin/event/{eventId}/availablecom")
    public ResponseEntity<EventFullDto> availableComment(@PathVariable Long eventId) {
        log.info("EWM-Server: Change event available comment {}", eventId);
        return ResponseEntity.ok(commentService.availableComment(eventId));
    }


    @GetMapping("comments")
    public ResponseEntity<List<CommentDto>> get(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                @RequestParam(name = "eventId", required = false) Long eventId,
                                                @RequestParam(name = "userId", required = false) Long userId) {
        log.info("EWM-Server: get comments list from {}, size {}", from, size);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(commentService.getCommentByEventId(pageRequest, eventId, userId));
    }

    @PatchMapping("comment/{comId}/user/{userId}")
    public ResponseEntity<CommentDto> ban(@PathVariable Long comId,
                                          @PathVariable Long userId,
                                          @Valid @RequestBody CommentDto commentDto) {
        log.info("EWM-Server: Change comment comId: {}, userId: {}, comment: {} ", comId, userId, commentDto);
        return ResponseEntity.ok(commentService.change(userId, comId, commentDto));
    }
}
