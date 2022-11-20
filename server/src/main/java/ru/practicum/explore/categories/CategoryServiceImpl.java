package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore.categories.dto.CategoryDto;
import ru.practicum.explore.categories.dto.CategoryMapper;
import ru.practicum.explore.exceptions.ConflictExceptions;
import ru.practicum.explore.exceptions.IncorrectRequest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDto add(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ConflictExceptions("имя занято");
        }
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        if (categoryRepository.existsById(catId)) categoryRepository.deleteById(catId);
    else throw new IncorrectRequest("Категория не найдена");
    }

    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new ConflictExceptions("имя занято");
        }
        Category category1 = categoryMapper.toCategory(categoryDto);
        category1.setName(category1.getName());
        return categoryMapper.toDto(categoryRepository.save(category1));
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return categoryMapper.toDto(categoryRepository.findById(catId)
                .orElseThrow(() -> new IncorrectRequest("категория не найдена")));
    }

    @Override
    public List<CategoryDto> getCategoryList(PageRequest pageRequest) {

        return categoryRepository.findAll(pageRequest)
                .stream()
                .map(categoryMapper::toDto).collect(Collectors.toList());
    }
}
