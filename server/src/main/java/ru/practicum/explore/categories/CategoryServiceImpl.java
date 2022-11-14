package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper category;

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category1 = category.toCategory(categoryDto);
        System.out.println(category1);
        return category.toDto(categoryRepository.save(category1));
    }
}
