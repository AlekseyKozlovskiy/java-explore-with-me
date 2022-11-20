package ru.practicum.explore.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    List<Event> findAllByCategoryId(Long id);

    Event findAllByInitiatorIdAndId(Long userId, Long eventId);

    Page<Event> findAll(Specification<Event> specification, Pageable pageable);

}