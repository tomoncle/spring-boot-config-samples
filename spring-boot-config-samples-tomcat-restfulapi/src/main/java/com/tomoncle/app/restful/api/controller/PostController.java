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
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/rest/post")
public class PostController {
    private static Logger logger = LoggerFactory.getLogger(PostController.class);

    /**
     * curl -i -X POST \
     * -H "Content-Type:application/x-www-form-urlencoded" \
     * -d \
     * '{"data":[{"sourcePort":12982,"sourceIp":"119.12.15.55","url":"http://www.baidu.com/abc.html","timestamp":1589445557967}]}' \
     * 'http://localhost:8910/rest/post-form-urlencoded'
     *
     * @param map json data
     * @return JSONObject
     */
    @PostMapping(value = "/form-urlencoded", headers = {"Content-Type=application/x-www-form-urlencoded"})
    public JSONObject anonymous(@RequestParam HashMap map) {
        if (map.keySet().isEmpty()) {
            return new JSONObject();
        }
        String jsonData = map.keySet().iterator().next().toString();
        logger.debug("form-urlencoded anonymous: " + jsonData);
        return JSONObject.parseObject(jsonData);
    }

    /**
     * curl -X POST --header "Content-Type: application/x-www-form-urlencoded" \
     * "http://localhost:8910/rest/post-form-urlencoded?map=%7B%22data%22%3A%5B%7B%22sourcePort%22%3A12982%2C%22sourceIp%22%3A%22119.12.15.55%22%2C%22url%22%3A%22http%3A%2F%2Fwww.baidu.com%2Fabc.html%22%2C%22timestamp%22%3A1589445557967%7D%5D%7D"
     *
     * @param map json data
     * @return JSONObject
     */
    @PostMapping(value = "/form-urlencoded", params = {"map"}, headers = {"Content-Type=application/x-www-form-urlencoded"})
    public JSONObject specify(@RequestParam HashMap map) {
        String jsonData = map.get("map").toString();
        logger.debug("form-urlencoded specify: " + jsonData);
        return JSONObject.parseObject(jsonData);
    }

    /**
     * curl -i -X POST \
     * -H "Content-Type:multipart/form-data" \
     * -F "file=@\"./狗狗连喊带比划叫小伙伴出门玩.mp4\";type=video/mp4;filename=\"狗狗连喊带比划叫小伙伴出门玩.mp4\"" \
     * 'http://localhost:8910/rest/post/form-data'
     *
     * @param file file
     * @return json
     */
    @PostMapping(value = "/form-data", headers = {"Content-Type=multipart/form-data"})
    public JSONObject formDataAnonymous(@RequestParam("file") MultipartFile file) {
        logger.debug("form-data anonymous: " + file.getOriginalFilename());
        return new JSONObject(new HashMap<String, Object>() {{
            put("file", file.getOriginalFilename());
        }});
    }

    /**
     * curl -i -X POST \
     * -H "Content-Type:multipart/form-data" \
     * -F "username=tomoncle" \
     * -F "password=123456" \
     * -F "file=@\"./狗狗连喊带比划叫小伙伴出门玩.mp4\";type=video/mp4;filename=\"狗狗连喊带比划叫小伙伴出门玩.mp4\"" \
     * 'http://localhost:8910/rest/post/form-data'
     *
     * @param file     file
     * @param username string
     * @param password string
     * @return json
     */
    @PostMapping(value = "/form-data", params = {"username", "password"}, headers = {"Content-Type=multipart/form-data"})
    public JSONObject formDataAnonymous(@RequestParam("file") MultipartFile file,
                                        @RequestParam String username,
                                        @RequestParam String password) {
        logger.debug("form-data anonymous: " + file.getOriginalFilename() + username + password);
        // 测试使用，禁止生产环境这样写
        return new JSONObject(new HashMap<String, Object>() {{
            put("file", file.getOriginalFilename());
            put("username", username);
            put("password", password);
        }});
    }

    /**
     * curl -i -X POST \
     * -H "Content-Type:multipart/form-data" \
     * -F "username=12" \
     * -F "age=12" \
     * 'http://localhost:8910/rest/post/form-data-object'
     *
     * @param map 匿名hash参数，传入key=value
     * @return json
     */
    @PostMapping(value = "/form-data-map", headers = {"Content-Type=multipart/form-data"})
    public JSONObject formDataAnonymous(@RequestParam HashMap map) {
        logger.debug("form-data-map anonymous: " + map);
        return (JSONObject) JSONObject.toJSON(map);
    }

    /**
     * curl -i -X POST \
     * -H "Content-Type:application/json" \
     * -d \
     * '{"data":[{"sourcePort":12982,"sourceIp":"119.12.15.55","url":"http://www.baidu.com/abc.html","timestamp":1589445557967}]}' \
     * 'http://localhost:8910/rest/post/body-json'
     *
     * @param jsonObject json
     * @return json
     */
    @PostMapping(value = "/body-json", headers = {"Content-Type=application/json"})
    public JSONObject anonymous(@RequestBody JSONObject jsonObject) {
        logger.debug("body-json anonymous: " + jsonObject);
        return jsonObject;
    }


    @Data
    public static class UserForm {
        private String username;
        private String age;
        private String password;
        private MultipartFile file;
    }

}
