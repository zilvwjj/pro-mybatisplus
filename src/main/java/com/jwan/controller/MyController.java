package com.jwan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jenkins")
public class MyController {
    @RequestMapping("/hello")
    public String hello () {
        return "helloWorld";
    }
}
