package ru.practicum.explore.event;

import lombok.*;
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
    Integer id;

    @Column(name = "annotation")
    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @Column(name = "confirmed_requests")
    Boolean confirmedRequests;

    @Column(name = "created_on")
    LocalDateTime createdOn;

    @Column(name = "description")
    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @Column(name = "paid")
    Boolean paid;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;

    @Column(name = "participant_limit")
    Long participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    Boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    State state;

    @Column(name = "title")
    String title;

    @Column(name = "views")
    Long views;

    @Column(name = "lat")
    Float lat;

    @Column(name = "lon")
    Float lon;

}