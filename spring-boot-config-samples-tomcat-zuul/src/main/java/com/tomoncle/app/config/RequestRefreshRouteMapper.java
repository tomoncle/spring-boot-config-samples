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

package com.tomoncle.app.config;


import com.tomoncle.config.springboot.zuul.config.RouteUpdater;
import com.tomoncle.config.springboot.zuul.mapper.IRouteMapper;
import com.tomoncle.config.springboot.zuul.mapper.MemoryStorageRouteMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态维护请求地址不变的uri, 根据参数代理不同的后端
 * <p/>
 * http://127.0.0.1:8090/proxy/static/?region=dev  返回 dev 环境的服务
 * http://127.0.0.1:8090/proxy/static/?region=test 返回 test 环境的服务
 */
@Component
public class RequestRefreshRouteMapper implements IRouteMapper {
    private static Logger logger = LogManager.getLogger(RequestRefreshRouteMapper.class);

    private static final Map<String, String> LOCAL_DB = new HashMap<>();

    public RequestRefreshRouteMapper() {
        LOCAL_DB.put("test", "http://192.168.116.18:8080/");
        LOCAL_DB.put("dev", "http://172.16.102.16:8080/");
    }


    @Autowired
    MemoryStorageRouteMapper storageRouteMapper;

    @Autowired
    RouteUpdater routeUpdater;

    @Override
    public void refresh(ServletRequest servletRequest, ServletResponse servletResponse) {
        if (null == storageRouteMapper) {
            logger.warn("MemoryTemporaryRoute Bean is null, zuul proxy route refresh fail.");
        } else {
            // 使用过滤器来自动更新"/static/**"这个请求路径，转发的url链接
            final String region = servletRequest.getParameter("region");
            if (StringUtils.isNotEmpty(region) && LOCAL_DB.containsKey(region)) {
                final String url = LOCAL_DB.get(region);
                if (StringUtils.isNotEmpty(url)) {
                    storageRouteMapper.addOrReplace("/static/**", url);
                    routeUpdater.refresh();
                }
            }
        }
    }
}
