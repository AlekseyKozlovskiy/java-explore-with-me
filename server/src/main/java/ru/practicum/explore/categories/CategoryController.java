package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.categories.dto.CategoryDto;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    ResponseEntity<CategoryDto> add(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("EWM-Server: Add new category {}", categoryDto);
        return ResponseEntity.ok(categoryService.add(categoryDto));

    }

    @DeleteMapping("/admin/categories/{catId}")
    void delete(@PathVariable("catId") Long catId) {
        log.info("EWM-Server: Delete category {}", catId);
        categoryService.delete(catId);
    }

    @PatchMapping("/admin/categories")
    ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("EWM-Server: Update category {}", categoryDto);
        return ResponseEntity.ok(categoryService.update(categoryDto));
    }

    @GetMapping("/categories/{catId}")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable("catId") Long catId) {
        log.info("EWM-Server: get category {}", catId);
        return ResponseEntity.ok(categoryService.getCategory(catId));

    }

    @GetMapping("/categories")
    ResponseEntity<List<CategoryDto>> getCategoryList(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("EWM-Server: get category list from{} size{}", from, size);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(categoryService.getCategoryList(pageRequest));
    }

}
