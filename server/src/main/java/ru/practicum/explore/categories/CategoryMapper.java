package ru.practicum.explore.categories;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
//    @Mapping(target = "id", ignore = true)
    CategoryDto toDto(Category category);

    Category toCategory(CategoryDto categoryDto);

}
