package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatController {
    private final StatService statService;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    public void add(@RequestBody EndpointHitDto endpointHitDto) {
        statService.add(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam String start,
                                    @RequestParam String end,
                                    @RequestParam Set<String> uris,
                                    @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        List<ViewStats> statsList = statService.getStats(start, end, uris, unique);
        return statsList;
    }

}
