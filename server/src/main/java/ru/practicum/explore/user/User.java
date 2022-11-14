package ru.practicum.explore.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", schema = "ewm")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    String name;

    @Email
    @NotBlank
    @Column(name = "email")
    String email;

}
