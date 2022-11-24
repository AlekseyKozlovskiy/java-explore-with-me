package ru.practicum.explore.participation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    Participation findByRequesterIdAndEventId(Long userId, Long eventId);

    List<Participation> findAllByRequesterId(Long userId);

    List<Participation> findByEventId(long eventId);
}
