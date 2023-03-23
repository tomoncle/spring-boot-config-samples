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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tomoncle
 */
@RestController
public class RestApiController {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    public RestApiController(UserRepository userRepository, PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users")
    List<User> users() {
        return userRepository.findAll();
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
