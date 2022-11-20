package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.categories.dto.CategoryDto;

import java.util.List;
//import ru.practicum.explore.user.UserFein;

@RestController()
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
//    private final UserFein userFein;

    @PostMapping("/admin/categories")
    CategoryDto add(@Validated @RequestBody CategoryDto categoryDto) {
//        userFein.t();
        CategoryDto categoryDto1 = categoryService.add(categoryDto);
        return categoryDto1;

    }

    @DeleteMapping("/admin/categories/{catId}")
    void delete(@PathVariable("catId") Long catId) {
        categoryService.delete(catId);
    }

    @PatchMapping("/admin/categories")
    ResponseEntity<CategoryDto> update(@Validated @RequestBody CategoryDto categoryDto) {
        log.info("EWM-Server: Add new category {}", categoryDto);
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }

    @GetMapping("/categories/{catId}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable("catId") Long catId) {
        return ResponseEntity.ok(categoryService.getCategory(catId));

    }

    @GetMapping("/categories")
    ResponseEntity<List<CategoryDto>> getCategoryList(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(categoryService.getCategoryList(pageRequest));
    }

}
