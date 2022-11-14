package ru.practicum.explore.categories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import ru.practicum.explore.user.UserFein;

@RestController()
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;
//    private final UserFein userFein;

    @PostMapping("/categories")
    ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
//        userFein.t();
        log.info("EWM-Server: Add new category {}", categoryDto);
        return ResponseEntity.ok(categoryService.add(categoryDto));

    }
}
