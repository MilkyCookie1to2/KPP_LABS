package com.bsuir.kpp;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller {

    @GetMapping("/less")
    public int less(@RequestParam(value = "value") int value) {
        return (int)(Math.random() * ++value);
    }
    @GetMapping("/more")
    public int more(@RequestParam(value = "value") int value) {
        return (int)(Math.random()*100)+value;
    }

}
