package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.utils.FormatterDate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository statRepository;
    private final EndpointMapper endpointMapper;

    @Transactional
    public void add(EndpointHitDto endpointHitDto) {
        statRepository.save(endpointMapper.toEndpoint(endpointHitDto));
    }

    public List<ViewStats> getStats(String start, String end, Set<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, FormatterDate.formatter());
        LocalDateTime endTime = LocalDateTime.parse(end, FormatterDate.formatter());
        List<ViewStats> statsList = new ArrayList<>();
        if (uris.isEmpty())
            return statsList;

        for (String uri : uris) {
            Integer hits;
            if (unique) {
                hits = statRepository.findUniqueIpUriHits(uri, startTime, endTime)
                        .orElseThrow(() -> new RuntimeException("Ошибка в запросе к бд"));
            } else {
                hits = statRepository.findUriHits(uri, startTime, endTime)
                        .orElseThrow(() -> new RuntimeException("Ошибка в запросе к бд"));
            }
            ViewStats viewStats = new ViewStats("main-service", uri, hits);
            statsList.add(viewStats);
        }

        return statsList;
    }
}