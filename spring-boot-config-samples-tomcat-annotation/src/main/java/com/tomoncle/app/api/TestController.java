package com.tomoncle.app.api;

import com.tomoncle.app.api.service.TestService;
import com.tomoncle.config.springboot.annotation.SilentError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    TestService testService;


    public TestController() {
        System.out.println("load TestController.");
    }

    class Student {
        private String username;
        private int age;
    }

    @SilentError
    @GetMapping("/hello")
    public Object hello(String hello, Integer size) {
        System.out.println(1 / 0);
        return "hello world!" + hello + size;
    }

    @GetMapping("/success")
    public Object success() {
        // 正确的使用方法
        System.out.println("**************" + testService.hello("", 0) + "**************");
        return "hello world!";
    }

    @GetMapping("/error")
    public Object error() {
        // 错误的使用方法
        System.out.println("**************" + this.hello("", 0) + "**************");
        return "hello world!";
    }

}
