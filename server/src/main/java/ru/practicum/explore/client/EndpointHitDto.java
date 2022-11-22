package ru.practicum.explore.client;

import lombok.Data;

@Data
public class EndpointHitDto {
    String app;
    String uri;
    String ip;
    String timestamp;

//    public EndpointHit(String app, URI uri, String ip) {
//        this.app = app;
//        this.uri = uri;
//        this.ip = ip;
//        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(timestamp);
//    }

    public static void main(String[] args) {
//        LocalDateTime.parse(dto.getEventDate(), FORMATTER)
//        String timeStamp = LocalDateTime.parse(LocalDateTime.now().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
//        LocalDateTime localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        String localDateTime1 = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString();
//        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        System.out.println(timeStamp);
    }
}