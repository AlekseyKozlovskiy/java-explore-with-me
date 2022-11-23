package ru.practicum.explore.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FeignClient(name = "stat", url = "http://ewm-stats:9090")
public interface Fein {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static EndpointHitDto toEndpointHitDto(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setUri(request.getRequestURI());
        endpointHitDto.setApp("ewm-server");
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setTimestamp(LocalDateTime.now().format(FORMATTER));
        System.out.println(endpointHitDto);
        return endpointHitDto;
    }

    @PostMapping("/hit")
    void addToStat(@RequestBody EndpointHitDto endpointHitDto);

}
