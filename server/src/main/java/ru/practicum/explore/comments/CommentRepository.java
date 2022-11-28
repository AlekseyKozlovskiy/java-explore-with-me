package ru.practicum.explore.comments;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(Long eventId, Pageable pageable);

    List<Comment> findAllByUserId(Long eventId, Pageable pageable);

    List<Comment> findAllByEventIdAndUserId(Long eventId, Long userId, Pageable pageable);

}
