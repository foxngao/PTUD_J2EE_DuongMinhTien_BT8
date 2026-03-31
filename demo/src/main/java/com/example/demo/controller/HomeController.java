package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/home")
    @ResponseBody
    public String home(Principal principal) {
        return "Hello, " + principal.getName();
    }
}
