/*
 * Copyright 2018 tomoncle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tomoncle.app.api;

import com.tomoncle.app.api.service.TestService;
import com.tomoncle.config.springboot.model.SilentError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ss")
public class TestController {

    private final TestService testService;


    public TestController(TestService testService) {
        System.out.println("load TestController.");
        this.testService = testService;
    }

    class Student {
        private String username;
        private int age;
    }


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

    @SilentError
    @GetMapping("/error")
    public Object error() {
        // 错误的使用方法
        System.out.println("**************" + this.hello("", 0) + "**************");
        return "hello world!";
    }

}
