package com.tomoncle.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // ignore this controller for swagger ui.
@Controller
public class SwaggerController {

    @GetMapping("/")
    public String welcome() {
        return "redirect:/swagger-ui.html";
    }

}
