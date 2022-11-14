package ru.practicum.explore.compilations;

import lombok.*;

import javax.persistence.*;

@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compilations", schema = "ewm")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "pinned")
    boolean pinned;

    @Column(name = "title")
    String title;
}
