package com.jwan.controller;

import com.jwan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jenkins")
public class MyController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello () {
        return "helloWorld";
    }
}
