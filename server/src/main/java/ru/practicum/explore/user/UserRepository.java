package ru.practicum.explore.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByName(String name);

    List<User> findUsersByIdIn(List<Long> userIds, Pageable pageable);

}
