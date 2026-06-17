package org.example.gestion_product_spring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }
}
