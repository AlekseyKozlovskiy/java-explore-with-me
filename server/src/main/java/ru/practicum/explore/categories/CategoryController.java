package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.categories.dto.CategoryDto;
//import ru.practicum.explore.user.UserFein;

@RestController()
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
//    private final UserFein userFein;

    @PostMapping
    ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
//        userFein.t();
        log.info("EWM-Server: Add new category {}", categoryDto);
        return ResponseEntity.ok(categoryService.add(categoryDto));

    }

    @DeleteMapping("/{catId}")
    void delete(@PathVariable("catId") Long catId) {
        categoryService.delete(catId);
    }

    @PatchMapping
    ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto) {
        log.info("EWM-Server: Add new category {}", categoryDto);
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }

}
