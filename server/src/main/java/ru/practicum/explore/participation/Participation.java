package ru.practicum.explore.participation;

import lombok.*;
import ru.practicum.explore.event.State;

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

    @Column(name = "event_id")
    Long event;

    @Column(name = "user_id")
    Long requesterId;

    @Column(name = "created")
    LocalDateTime created;

    @Column
    @Enumerated(EnumType.STRING)
    State status;

}
