package ru.practicum.explore.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.explore.client.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@FeignClient(name = "kjk", url = "http://localhost:9090")
public interface UserFein {
    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static EndpointHitDto t(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setUri(request.getRequestURI());
        endpointHitDto.setApp("ewm-server");
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setTimestamp(LocalDateTime.now().format(FORMATTER));
        System.out.println(endpointHitDto);
//        eventClient.saveHit(endpointHit);

//        URI uri = URI.create(request.getRequestURI());
//        String ip = request.getRemoteAddr();
//        EndpointHit endpointHit = new EndpointHit("test", uri, ip);
//        System.out.println("!!!!!!!" + endpointHit);
        return endpointHitDto;
    }
//                   @RequestParam("uri") URI uri,
//                   @RequestParam("ip") String ip,
//                   @RequestParam("timeStamp") String timeStamp);

    @PostMapping("/hit")
    void addToStat(@RequestBody EndpointHitDto endpointHitDto);

    @PostMapping("/test")
    void rr();

}
