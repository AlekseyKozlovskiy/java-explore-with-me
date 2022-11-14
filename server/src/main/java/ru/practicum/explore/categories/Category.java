package ru.practicum.explore.categories;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories", schema = "ewm")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;
}
