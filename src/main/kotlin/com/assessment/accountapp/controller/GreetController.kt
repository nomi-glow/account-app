package com.assessment.accountapp.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetController {
    @GetMapping("/greet")
    fun greet(): String {
        return "Hello, World"
    }
}