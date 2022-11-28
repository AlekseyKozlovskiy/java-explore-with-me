package ru.practicum.explore.event;

import lombok.Data;
import ru.practicum.explore.categories.Category;
import ru.practicum.explore.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "events", schema = "ewm")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "annotation", nullable = false)
    private String annotation;

    @JoinColumn(name = "category_id")
    @ManyToOne
    private Category category;

    @Column(name = "confirmed_requests", nullable = false)
    private Boolean confirmedRequests;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

    @JoinColumn(name = "initiator_id")
    @ManyToOne
    private User initiator;

    @Column(name = "participant_limit", nullable = false)
    private Long participantLimit;

    @Column(name = "published_on", nullable = false)
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "views", nullable = false)
    private Long views;

    @Column(name = "lat", nullable = false)
    private Float lat;

    @Column(name = "lon", nullable = false)
    private Float lon;

    @Column(name = "availability_of_comments", nullable = false)
    private Boolean availabilityOfComments;

}