package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StatService {
    private final StatRepository statRepository;
    private final EndpointMapper endpointMapper;


    void add(EndpointHitDto endpointHitDto) {
        statRepository.save(endpointMapper.toEndpoint(endpointHitDto));
    }

    public List<ViewStats> getStats(String start, String end, Set<String> uris, Boolean unique) {
        LocalDateTime startTime = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endTime = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<ViewStats> statsList = new ArrayList<>();
        if (uris.isEmpty())
            return statsList;

        for (String uri : uris) {
            Integer hits;
            if (unique) {
                hits = statRepository.findUniqueIpUriHits(uri, startTime, endTime)
                        .orElseThrow(() -> new RuntimeException("DB extraction error"));
            } else {
                hits = statRepository.findUriHits(uri, startTime, endTime)
                        .orElseThrow(() -> new RuntimeException("DB extraction error"));
            }
            ViewStats viewStats = new ViewStats("main-service", uri, hits);
            statsList.add(viewStats);
        }

        return statsList;
    }
}