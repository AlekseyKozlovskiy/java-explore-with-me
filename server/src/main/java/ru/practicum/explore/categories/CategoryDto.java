package ru.practicum.explore.categories;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {
    private Integer id;

//    @NotBlank
    private String name;


    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
