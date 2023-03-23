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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;


/**
 * @author tomoncle
 */
@Configuration
public class WebAppAccessDecisionManager implements AccessDecisionManager {

    private static Logger logger = LogManager.getLogger(WebAppAccessDecisionManager.class);

    /**
     * 判断当前用户是否具备指定的角色
     *
     * @param authentication 当前登录用户的信息，包含角色信息
     * @param o              FilterInvocation 当前请求的信息
     * @param collection     当前请求所需要的的角色
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws
            AccessDeniedException, InsufficientAuthenticationException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // 匹配白名单权限的路径，赋予匿名访问权限, 此处不再使用，转到 WebSecurityConfigurerAdapter.configure(WebSecurity web)
        //        AntPathMatcher pathMatcher = new AntPathMatcher();
        //        for(String pattern: webAppSecurityProperties.getAllowSet()){
        //            if (pathMatcher.match(pattern, requestUrl)) {
        //                return;
        //            }
        //        }
        logger.info("🔚🔚 请求路径：" + requestUrl
                + " , 需要角色：" + collection
                + " , 当前用户拥有角色：" + authentication.getAuthorities());

        // 判断当前用户是否授权
        for (ConfigAttribute attribute : collection) {
            // 访客权限
            if (SecurityRoleManager.ROLE_GUEST.name().equals(attribute.getAttribute())) {
                //匿名登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("401，请登录后重试!");
                }
                return;
            }
            // 认证权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(attribute.getAttribute())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("401，请求受限!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}