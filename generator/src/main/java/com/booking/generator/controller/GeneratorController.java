package com.booking.generator.controller;

import com.booking.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generator")
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping("/generate")
    public ResponseEntity<Void> generate() {
        generatorService.generate();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/book")
    public ResponseEntity<Void> book() {
        generatorService.book();
        return ResponseEntity.ok().build();
    }
}
