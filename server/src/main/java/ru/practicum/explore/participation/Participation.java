package ru.practicum.explore.participation;

import lombok.*;
import ru.practicum.explore.event.Event;
import ru.practicum.explore.event.State;
import ru.practicum.explore.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "participation_requests", schema = "ewm")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requester;

    @Column(name = "created")
    LocalDateTime created;

    @Column
    @Enumerated(EnumType.STRING)
    State status;

}
