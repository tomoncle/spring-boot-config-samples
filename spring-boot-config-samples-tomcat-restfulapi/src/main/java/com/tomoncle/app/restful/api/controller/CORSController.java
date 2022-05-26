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

package com.tomoncle.app.restful.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/cors/api")
public class CORSController {
    private static Logger logger = LoggerFactory.getLogger(CORSController.class);

    @PostMapping(value = "/body-json")
    @CrossOrigin(origins = "*")
    public JSONObject anonymous(@RequestBody JSONObject jsonObject) {
        logger.debug("body-json anonymous: " + jsonObject);
        return jsonObject;
    }


    /**
     * 使用nginx配置，以实现后端跨域请求，后端代码不用修改
     *
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/body-json-by-ngx")
    public JSONObject anonymousNgx(@RequestBody JSONObject jsonObject) {
        /*
        location ^~ /cors/api/body-json-by-ngx {
            add_header Access-Control-Allow-Origin *;
            add_header Access-Control-Allow-Methods 'GET, POST, OPTIONS';
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';

            if ($request_method = 'OPTIONS') {
                return 204;
            }
            proxy_pass http://localhost:8910;
        }
         */
        logger.debug("body-json-by-ngx anonymousNgx: " + jsonObject);
        return jsonObject;
    }
}
