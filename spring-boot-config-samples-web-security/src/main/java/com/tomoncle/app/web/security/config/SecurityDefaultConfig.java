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

package com.tomoncle.app.web.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomoncle.app.web.security.entity.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomoncle
 */

/**
 * 默认角色配置
 */
enum SecurityRoleManager {
    // ROLE_GUEST    : any authenticated user, including users with the Guest role
    // ROLE_ANONYMOUS: unauthenticated user
    ROLE_GUEST, ROLE_ANONYMOUS
}

@Data
@Configuration
@ConfigurationProperties("app.permission.config")
class WebAppSecurityProperties {
    // app.permission.config.allow-set=/static/**,/favicon.ico
    private Set<String> allowSet = new HashSet<>();
    // app.permission.config.deny-set=/static/**,/favicon.ico
    private Set<String> denySet = new HashSet<>();
}

/**
 * 自定义认证成功处理器
 * <p>
 * 此配置 和 .defaultSuccessUrl("/home") 的配置会冲突，后配置的会覆盖前面配置
 */
class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        logger.info("认证成功 ！Logged user: " + authentication.getName());
        response.sendRedirect("/home");

    }
}

/**
 * 自定义认证失败处理器,
 * <p>
 * 此配置 和 .failureUrl("/login?error=true") 的配置会冲突，后配置的会覆盖前面配置
 */
class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException {

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        String message = String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime());
        logger.info("认证失败：" + message);

        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getOutputStream().println(message);
    }
}

/**
 * 自定义认证失败，处理
 */
class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", request.getRequestURI());
        map.put("message", "认证失败");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(map);
        response.getOutputStream().print(resBody);
    }
}

/**
 * 自定义授权失败，处理
 */
class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        map.put("url", request.getRequestURI());
        map.put("message", "访问受限");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper objectMapper = new ObjectMapper();
        String resBody = objectMapper.writeValueAsString(map);
        response.getOutputStream().print(resBody);
    }

}


/**
 * 方法级，权限验证
 */
@Configuration("sysExpr")
class CustomSecurityExpressionRoot {

    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String authorize : user.getAuthorizes()) {
            if (pathMatcher.match(authorize, authority)) {
                return true;
            }
        }
        return false;
    }
}