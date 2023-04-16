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

package com.tomoncle.app.web.jpa.api;

import com.tomoncle.app.web.jpa.dao.PermissionRepository;
import com.tomoncle.app.web.jpa.dao.RoleRepository;
import com.tomoncle.app.web.jpa.dao.UserRepository;
import com.tomoncle.app.web.jpa.entity.Permission;
import com.tomoncle.app.web.jpa.entity.Role;
import com.tomoncle.app.web.jpa.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tomoncle
 */
@RestController
public class RestApiController {
    private static Logger logger = LogManager.getLogger(RestApiController.class);
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public RestApiController(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * qps 3000/s
     *
     * @return String
     */
    @GetMapping("/healthy")
    String healthy() {
        logger.info(Thread.currentThread().getName());
        return "ok";
    }

    /**
     * qps 600/s
     *
     * @return List<User>
     */
    @GetMapping("/users")
    List<User> users() {
        logger.info(Thread.currentThread().getName());
        return userRepository.findAll();
    }

    /**
     * tps 1000/s
     *
     * @param user json
     * @return user
     */
    @PostMapping("/users")
    User saveUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @DeleteMapping("/users")
    void deleteUser() {
        userRepository.deleteAll();
    }

    @GetMapping("/roles")
    List<Role> roles() {
        return roleRepository.findAll();
    }

    @GetMapping("/permissions")
    List<Permission> permissions() {
        return permissionRepository.findAll();
    }

}
