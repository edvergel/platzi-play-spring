package com.platzi.play.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.platzi.play.domain.service.PlatziPlayAiService;

@RestController
public class HelloController {
    private final String platform;
    private final PlatziPlayAiService aiService;

    // Constructor para inyectar el valor de la propiedad y el servicio de IA
    public HelloController(@Value("${spring.application.name}") String platform, PlatziPlayAiService aiService) {
        this.platform = platform;
        this.aiService = aiService;
    }

    @GetMapping("/hello")
    public String hello() {
        return this.aiService.generateGreeting(platform);
    }
}
