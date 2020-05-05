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

package com.tomoncle.app.api;

import com.tomoncle.config.springboot.zuul.MemoryStorageRouteMapper;
import com.tomoncle.config.springboot.zuul.config.RefreshRouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 维护动态的代理列表
 */
@RestController("/-/reload")
public class DynamicProxyController {

    @Autowired
    MemoryStorageRouteMapper storageRouteMapper;

    @Autowired
    RefreshRouteConfiguration configuration;

    @PostMapping
    public String refresh(){
        configuration.refresh();
        return "ok!";
    }

    /**
     * 动态更新 zuul网关列表
     * @param path  qq
     * @param url   https://www.qq.com/
     * @return  更新后可以使用 http://{host}:{port}/{servletContentPath}/qq/ 来代理 https://www.qq.com/
     */
    @PutMapping
    public boolean update(@RequestParam("path") String path, @RequestParam("url") String url){
        storageRouteMapper.addOrReplace(String.format("/%s/**",path), url);
        configuration.refresh();
        return true;
    }

}
