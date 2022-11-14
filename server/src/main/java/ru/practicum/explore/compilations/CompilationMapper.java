package ru.practicum.explore.compilations;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.explore.compilations.dto.CompilationDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompilationMapper {

    CompilationDto toCompilationDto(Compilation compilation);

    Compilation toNewCompilation(CompilationDto compilationDto);

}
