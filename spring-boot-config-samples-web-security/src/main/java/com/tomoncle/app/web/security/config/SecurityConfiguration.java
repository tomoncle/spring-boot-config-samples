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

import com.tomoncle.app.web.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * docs: https://howtodoinjava.com/spring5/security/login-form-example/
 *
 * @author tomoncle
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final AuthService authService;
    private final WebAppAccessDecisionManager webAppAccessDecisionManager;
    private final WebAppFilter webAppFilter;
    private final WebAppSecurityProperties webAppSecurityProperties;

    public SecurityConfiguration(AuthService authService, WebAppAccessDecisionManager manager, WebAppFilter filter, WebAppSecurityProperties webAppSecurityProperties) {
        this.authService = authService;
        this.webAppAccessDecisionManager = manager;
        this.webAppFilter = filter;
        this.webAppSecurityProperties = webAppSecurityProperties;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService);
    }

    /**
     * 配置我们自定义的元数据源和权限决策配置
     *
     * @return ObjectPostProcessor<FilterSecurityInterceptor>
     */
    private ObjectPostProcessor<FilterSecurityInterceptor> getObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setAccessDecisionManager(webAppAccessDecisionManager);
                o.setSecurityMetadataSource(webAppFilter);
                return o;
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("配置访问策略.");
        // 配置 动态权限
        http.authorizeRequests().withObjectPostProcessor(getObjectPostProcessor());
        // 配置 记住我按钮
        http.rememberMe().tokenValiditySeconds(3600);
        // 配置登出
        http.logout()
                // 配置登出的地址，这个接口地址 Security 会自动生成
                .logoutUrl("/logout")
                // 登出成功后，跳转的地址
                .logoutSuccessUrl("/login?logout=true")
                // 清除会话信息
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        // 自定义认证成功、失败的拦截器,
        // 与.defaultSuccessUrl("/home").failureUrl("/login?error=true")冲突
        // 后配置的会覆盖前面配置
        http.formLogin()
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
        // 配置登录
        http.formLogin()
                // 这项配置如果自己配置了地址，需要自己在Controller中定义好该接口, 记得加入白名单
                .loginPage("/login")
                // 这项配置表示登录表单提交的地址, 这个接口地址 Security 会自动生成，post/From表单提交
                .loginProcessingUrl("/sign-in")
                // 自定义登录表单的属性，Security 会根据此处配置，自动配置后台的接收参数
                .usernameParameter("username")
                .passwordParameter("password")
                // 自定义认证成功、失败的页面或接口信息
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .permitAll();
        // 关闭CSRF
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        // 配置白名单
        web.ignoring().antMatchers(StringUtils.toStringArray(webAppSecurityProperties.getAllowSet()));
    }

}