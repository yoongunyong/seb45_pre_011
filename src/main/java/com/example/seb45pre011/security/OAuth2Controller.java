package com.example.seb45pre011.security;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2Controller {

        @GetMapping("/hello-oauth2")
        public String home() {
            return "hello-oauth2";
        }
    }


