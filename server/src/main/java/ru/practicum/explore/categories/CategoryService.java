package ru.practicum.explore.categories;

import ru.practicum.explore.categories.dto.CategoryDto;

public interface CategoryService {
    CategoryDto add(CategoryDto categoryDto);
    void  delete(Long catId);
    CategoryDto update(CategoryDto categoryDto);
}
