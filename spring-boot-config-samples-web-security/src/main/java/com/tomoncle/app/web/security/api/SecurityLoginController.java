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

package com.tomoncle.app.web.security.api;

import com.tomoncle.app.web.security.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tomoncle
 */
@Controller
@SuppressWarnings("unused")
public class SecurityLoginController {
    private static Logger logger = LogManager.getLogger(SecurityLoginController.class);

    /**
     * 登录成功，跳转页
     *
     * @return index.html
     */
    @GetMapping(value = {"/", "/home"})
    ModelAndView index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        logger.info("login success : " + user.getUsername());
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("nickname", user.getNickName());
        return new ModelAndView("index", map);
    }

    /**
     * 登录接口
     *
     * @return login.html
     */
    @GetMapping("/login")
    String login() {
        logger.info("access login page ...");
        return "login";
    }

}
