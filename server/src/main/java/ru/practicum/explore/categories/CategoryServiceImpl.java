package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.dto.CategoryDto;
import ru.practicum.explore.categories.dto.CategoryMapper;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        Category category1 = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryRepository.save(category1));
    }

    @Override
    public void delete(Long catId) {
        if (categoryRepository.existsById(catId)) {
            categoryRepository.deleteById(catId);
        }
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category1 = categoryMapper.toCategory(categoryDto);
        category1.setName(category1.getName());
        return categoryMapper.toDto(categoryRepository.save(category1));
    }
}
