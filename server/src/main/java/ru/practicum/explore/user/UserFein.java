package ru.practicum.explore.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kjk", url = "http://localhost:9090")
public interface UserFein {

    @PostMapping("/test")
    void t();
}
