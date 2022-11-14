package ru.practicum.explore.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Builder
@Valid
@Setter
@Getter
public class UserShortDto {
    private Long id;

    @NotBlank
    private String name;
}
