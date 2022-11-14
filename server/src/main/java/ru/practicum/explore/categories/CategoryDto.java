package ru.practicum.explore.categories;

import lombok.Data;

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
