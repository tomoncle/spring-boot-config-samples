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

import com.tomoncle.app.web.security.dao.PermissionRepository;
import com.tomoncle.app.web.security.entity.Permission;
import com.tomoncle.app.web.security.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.annotation.WebFilter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author tomoncle
 */
@Configuration
@WebFilter(urlPatterns = "*", filterName = "securityWebAppFilter")
@ConditionalOnWebApplication
public class WebAppFilter implements FilterInvocationSecurityMetadataSource {
    private static Logger logger = LogManager.getLogger(WebAppFilter.class);
    private final PermissionRepository permissionRepository;

    public WebAppFilter(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * 根据用户传来的请求地址，分析请求需要的角色，并将所需要的角色放在 Collection中
     *
     * @param o 当前请求的信息
     * @return 角色列表
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //获取请求地址
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 白名单配置， 此处不再使用，转到 WebSecurityConfigurerAdapter.configure(WebSecurity web)
        //        for(String pattern: webAppSecurityProperties.getAllowSet()){
        //            if (pathMatcher.match(pattern, requestUrl)) {
        //                // 匹配白名单权限的路径，赋予匿名访问权限
        //                logger.info(">>>>>> 请求路径："+ requestUrl + " , 白名单资源，允许匿名访问.");
        //                return SecurityConfig.createList(SecurityRoleManager.ROLE_ANONYMOUS.name());
        //            }
        //        }
        // 系统角色
        List<Permission> permissionList = permissionRepository.findAll();
        Set<String> attributeNames = new HashSet<>();
        for (Permission permission : permissionList) {
            if (permission.getRoles().size() == 0 || !StringUtils.hasText(permission.getPattern())) {
                continue;
            }
            if (!pathMatcher.match(permission.getPattern(), requestUrl)) {
                continue;
            }
            // 符合当前路径的角色信息
            for (Role role : permission.getRoles()) {
                attributeNames.add(role.getName());
            }
        }
        if (attributeNames.size() > 0) {
            logger.info("🔜🔜 请求路径：" + requestUrl + " , 授予角色：" + attributeNames);
            return SecurityConfig.createList(StringUtils.toStringArray(attributeNames));
        }
        // 没有配置权限的路径，赋予默认登录权限
        logger.info("🔜🔜 请求路径：" + requestUrl + " , 授予角色：" + SecurityRoleManager.ROLE_GUEST.name());
        return SecurityConfig.createList(SecurityRoleManager.ROLE_GUEST.name());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
