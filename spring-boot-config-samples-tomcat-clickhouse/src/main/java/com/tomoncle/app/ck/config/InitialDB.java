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

package com.tomoncle.app.ck.config;

import com.tomoncle.config.springboot.druid.DruidConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author tomoncle
 */
@Order(-2147483647)
@Component
public class InitialDB {
    private static Logger logger = LoggerFactory.getLogger(InitialDB.class);
    private final RestTemplate restTemplate;
    private final DruidConfigurationProperties properties;

    public InitialDB(RestTemplate restTemplate, DruidConfigurationProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
        initDatabase();
    }

    @SuppressWarnings("all")
    private void initDatabase() {
        String[] split = properties.getUrl().split("/");
        String hostname = split[2];
        String database = split[3].split("\\?")[0];
        String url = "http://" + hostname + "/?add_http_cors_header=1" +
                "&log_queries=1" +
                "&output_format_json_quote_64bit_integers=1" +
                "&output_format_json_quote_denormals=1" +
                "&user=" + properties.getUsername() +
                "&password=" + properties.getPassword() +
                "&database=" + database +
                "&result_overflow_mode=throw" +
                "&max_result_rows=5000" +
                "&timeout_overflow_mode=throw" +
                "&max_execution_time=5";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", "CREATE TABLE IF NOT EXISTS jpa_model ( " +
                "`event_date` Date DEFAULT CAST(now(), 'Date'), " +
                "`uuid` String DEFAULT generateUUIDv4()" +
                ") ENGINE = MergeTree(event_date, (uuid), 8192)");
        HttpEntity requestBody = new HttpEntity(map, headers);
        String object = this.restTemplate.postForObject(url, requestBody, String.class);
        logger.info(null == object ? "create jpa_model success!" : object);
    }

}
