package ru.practicum.explore.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilations.dto.CompilationDto;
import ru.practicum.explore.compilations.dto.NewDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompilationController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    public ResponseEntity<NewDto> addNewCompilation(@RequestBody CompilationDto newDto) {
        return ResponseEntity.ok(compilationService.createCompilation(newDto));
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<NewDto> get(@PathVariable("compId") Long compId) {
        return ResponseEntity.ok(compilationService.get(compId));
    }

    @GetMapping("/compilations")
    public ResponseEntity<List<NewDto>> get(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                                            @RequestParam(name = "pinned", required = false) Boolean pinned) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(compilationService.get(pinned, pageRequest));
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public ResponseEntity<NewDto> addEventCompilation(@PathVariable Long compId,
                                                      @PathVariable Long eventId) {
        return ResponseEntity.ok(compilationService.addEventToCompilation(compId, eventId));
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public ResponseEntity<NewDto> changePinned(@PathVariable Long compId) {
        return ResponseEntity.ok(compilationService.changePinned(compId));
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void delete(@PathVariable Long compId) {
        compilationService.delete(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void unPinned(@PathVariable Long compId) {
        compilationService.changePinned(compId);
    }

}
