package ru.practicum.explore.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Valid
@Setter
@Getter
@AllArgsConstructor
@Accessors(chain = true)
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @JsonCreator
    public UserDto(){

    }
}

