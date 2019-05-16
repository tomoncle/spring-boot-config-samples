package com.tomoncle.app.api;

import com.tomoncle.config.springboot.zuul.MemoryStorageRouteMapper;
import com.tomoncle.config.springboot.zuul.config.RefreshRouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        storageRouteMapper.addOrEdit(String.format("/%s/**",path), url);
        configuration.refresh();
        return true;
    }

}
