package ru.practicum.explore.categories.dto;

import org.mapstruct.Mapper;
import ru.practicum.explore.categories.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toCategory(CategoryDto categoryDto);

}
