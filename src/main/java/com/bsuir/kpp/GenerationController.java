package com.bsuir.kpp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerationController {

    @GetMapping("/less")
    public GeneratedNumber generateLess(@RequestParam(value = "number", defaultValue = "0") int number) {
        return new GeneratedNumber((int) (Math.random() * ++number));

    }

    @GetMapping("/more")
    public GeneratedNumber generateMore(@RequestParam(value = "number", defaultValue = "0") int number) {
        return new GeneratedNumber((int) (Math.random() * 100 + number));
    }
}
