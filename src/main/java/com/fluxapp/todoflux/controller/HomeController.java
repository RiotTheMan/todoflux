package com.fluxapp.todoflux.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping
    public String home() {
        return "Hello and stuff";
    }
}
