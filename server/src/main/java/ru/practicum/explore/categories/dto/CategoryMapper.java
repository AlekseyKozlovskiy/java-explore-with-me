package ru.practicum.explore.categories.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.explore.categories.Category;
import ru.practicum.explore.categories.dto.CategoryDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    //    @Mapping(target = "id", ignore = true)
    CategoryDto toDto(Category category);

    Category toCategory(CategoryDto categoryDto);

}
