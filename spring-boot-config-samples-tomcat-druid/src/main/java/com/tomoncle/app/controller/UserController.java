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

package com.tomoncle.app.controller;

import com.tomoncle.app.common.DistributedLock;
import com.tomoncle.app.entity.Card;
import com.tomoncle.app.entity.User;
import com.tomoncle.app.service.UserService;
import lombok.SneakyThrows;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> list() {
        return userService.findAll();
    }

    @GetMapping("/page")
    public Page<User> page(@RequestParam("backName") String backName,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "rows", defaultValue = "10") int rows) {
        User user = new User();
        Card card = new Card();
        card.setBackName(backName);
        user.setCard(card);
        return userService.users(user, page, rows);
    }


    @GetMapping("/get")
    public User get(@RequestParam("backName") String backName) {
        User user = new User();
        Card card = new Card();
        card.setBackName(backName);
        user.setCard(card);
        return userService.findOne(Example.of(user));
    }


    @SneakyThrows
    @PostMapping("/save")
    public User save(User user) {
        String certificate = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(Thread.currentThread().getName() + " 入参信息: " + user.toString() + " :-: " + certificate);
        DistributedLock lock = DistributedLock.getInstance();
        if (lock.lock(user.getUsername(), 60)) {
            User data = userService.saveUnique(user);
            lock.unlock(user.getUsername());
            return data;
        }
        throw new RuntimeException("请求超时，请重试");
    }


}
