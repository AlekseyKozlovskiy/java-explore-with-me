package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select count(*) from ewm_stats.stat as s " +
            "where s.uri=?1 and s.timestamp between ?2 and ?3", nativeQuery = true)
    Optional<Integer> findUriHits(String uri, LocalDateTime start, LocalDateTime end);

    @Query(value = "select count(DISTINCT s.ip) from ewm_stats.stat as s " +
            "where s.uri=?1 and s.timestamp between ?2 and ?3", nativeQuery = true)
    Optional<Integer> findUniqueIpUriHits(String uri, LocalDateTime start, LocalDateTime end);
}
