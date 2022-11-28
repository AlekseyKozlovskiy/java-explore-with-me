package ru.practicum.explore.comments.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private LocalDateTime created = LocalDateTime.now();
    @NotBlank
    private String comment;
    private Long eventId;
    private Long userId;
}
