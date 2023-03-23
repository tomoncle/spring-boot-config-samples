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

import com.tomoncle.app.web.security.dao.PermissionRepository;
import com.tomoncle.app.web.security.dao.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class WebController {
    private final RequestMappingHandlerMapping handlerMapping;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public WebController(RequestMappingHandlerMapping h, UserRepository u, PermissionRepository p) {
        this.handlerMapping = h;
        this.userRepository = u;
        this.permissionRepository = p;
    }

    @GetMapping("/routers")
    ResponseInfo routers() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods();
        Set<String> routers = new HashSet<>();
        for (RequestMappingInfo info : handlerMethods.keySet()) {
            routers.addAll(info.getPatternValues());
        }
        return new ResponseInfo("API列表", routers);
    }

    @GetMapping("/users")
    ResponseInfo users() {
        return new ResponseInfo("用户列表", userRepository.findAll());
    }

    @GetMapping("/permissions")
    ResponseInfo permissions() {
        return new ResponseInfo("资源列表", permissionRepository.findAll());
    }

}

@Data
@AllArgsConstructor
class ResponseInfo {
    private String message;
    private Object data;
}