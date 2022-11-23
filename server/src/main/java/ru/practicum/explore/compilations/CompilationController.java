package ru.practicum.explore.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore.compilations.dto.CompilationDto;
import ru.practicum.explore.compilations.dto.NewDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CompilationController {
    private final CompilationService compilationService;

    @PostMapping("/admin/compilations")
    public ResponseEntity<NewDto> addNewCompilation(@Validated @RequestBody CompilationDto newDto) {
        log.info("EWM-Server: add compilation {}", newDto);
        return ResponseEntity.ok(compilationService.createCompilation(newDto));
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<NewDto> get(@PathVariable("compId") Long compId) {
        log.info("EWM-Server: get compilation by Id {}", compId);
        return ResponseEntity.ok(compilationService.get(compId));
    }

    @GetMapping("/compilations")
    public ResponseEntity<List<NewDto>> get(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                                            @RequestParam(name = "pinned", required = false) Boolean pinned) {
        log.info("EWM-Server: get compilation list from {}, size {}, pinned {}", from, size, pinned);
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(compilationService.get(pinned, pageRequest));
    }

    @PatchMapping("/admin/compilations/{compId}/events/{eventId}")
    public ResponseEntity<NewDto> addEventCompilation(@PathVariable Long compId,
                                                      @PathVariable Long eventId) {
        log.info("EWM-Server: add Event Compilation, compilation id{}, event id", compId, eventId);
        return ResponseEntity.ok(compilationService.addEventToCompilation(compId, eventId));
    }

    @PatchMapping("/admin/compilations/{compId}/pin")
    public ResponseEntity<NewDto> changePinned(@PathVariable Long compId) {
        log.info("EWM-Server: change Pinned, compilation id{}", compId);
        return ResponseEntity.ok(compilationService.changePinned(compId));
    }

    @DeleteMapping("/admin/compilations/{compId}")
    public void delete(@PathVariable Long compId) {
        log.info("EWM-Server: delete compilation id{}", compId);
        compilationService.delete(compId);
    }

    @DeleteMapping("/admin/compilations/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId,
                                           @PathVariable Long eventId) {
        log.info("EWM-Server: delete Event From Compilation, compilation id{}, event id{}", compId, eventId);

        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/admin/compilations/{compId}/pin")
    public void unPinned(@PathVariable Long compId) {
        log.info("EWM-Server: unPinned, compilation id{}", compId);
        compilationService.changePinned(compId);
    }

}
