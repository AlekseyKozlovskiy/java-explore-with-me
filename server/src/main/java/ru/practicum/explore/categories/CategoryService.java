package ru.practicum.explore.categories;

import org.springframework.data.domain.PageRequest;
import ru.practicum.explore.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto add(CategoryDto categoryDto);

    void delete(Long catId);

    CategoryDto update(CategoryDto categoryDto);

    CategoryDto getCategory(Long catId);

    List<CategoryDto> getCategoryList(PageRequest pageRequest);
}
