package com.tomoncle.app.api.service;

import com.tomoncle.config.springboot.annotation.SilentError;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class TestService {
    @SilentError
    public Object hello(String hello, Integer size) {
        System.out.println(1 / 0);
        return "hello world!" + hello + size;
    }
}
