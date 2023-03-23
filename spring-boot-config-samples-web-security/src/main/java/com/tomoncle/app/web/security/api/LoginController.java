///*
// * Copyright 2018 tomoncle
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.tomoncle.app.web.security.api;
//
//import com.tomoncle.app.web.security.dao.UserRepository;
//import com.tomoncle.app.web.security.entity.User;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * @author tomoncle
// */
//
//@Controller
//public class LoginController {
//    private static Logger logger = LogManager.getLogger(WebController.class);
//    private static final String SESSION_KEY = "current_user";
//    private final UserRepository userRepository;
//
//    public LoginController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    private boolean isLogin(HttpSession httpSession) {
//        return null != httpSession.getAttribute(SESSION_KEY);
//    }
//
//    @RequestMapping("/")
//    String index(HttpSession httpSession) {
//        logger.debug("welcome index page ...");
//        if (isLogin(httpSession)) {
//            return "index";
//        }
//        return "redirect:/login";
//    }
//
//    @GetMapping("/login")
//    String login(HttpSession httpSession) {
//        logger.debug("access login page ...");
//        if (isLogin(httpSession)) {
//            return "index";
//        }
//        return "login";
//    }
//
//    @PostMapping(value = "/login", headers = {"Content-Type=application/x-www-form-urlencoded"})
//    ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession httpSession) {
//        logger.info("login success " + username + " " + password);
//        User user = userRepository.findByUsername(username);
//        Map<String, Object> map = new HashMap<>();
//        if (!Objects.equals(user.getPassword(), password)) {
//            return new ModelAndView("login");
//        }
//        map.put("id", user.getId());
//        map.put("username", user.getUsername());
//        map.put("nickname", user.getNickName());
//
//        httpSession.setAttribute(SESSION_KEY, user);
//        return new ModelAndView("index", map);
//    }
//
//    @RequestMapping("/logout")
//    String logout(HttpSession httpSession) {
//        if (isLogin(httpSession)) {
//            httpSession.removeAttribute(SESSION_KEY);
//        }
//        return "login";
//    }
//}
