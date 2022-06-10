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

package com.tomoncle.springboot.nacos.c;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

/**
 * @author tomoncle
 */
@RestController("/api")
public class ServiceApiC {
    private static Logger logger = LoggerFactory.getLogger(ServiceApiC.class);
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/call-a")
    String service() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        try {
            ResponseEntity<String> exchange = restTemplate.exchange("http://nacos-service-a/web-a/api",
                    HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
            logger.warn("statusCode call-a: " + exchange.getStatusCode());
            return exchange.getBody();
        } catch (RestClientResponseException e) {
            logger.error("RestClientResponseException call-a: " + e.getRawStatusCode() + e.getStatusText() + e.getRawStatusCode());
        } catch (Exception e) {
            logger.error("Exception call-a: " + e);
        }
        return "null";
    }

    @GetMapping("/call-b")
    String serviceB() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        try {
            ResponseEntity<String> exchange = restTemplate.exchange("http://nacos-service-b/web-b/api",
                    HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
            logger.warn("statusCode call-b: " + exchange.getStatusCode());
            return exchange.getBody();
        } catch (RestClientResponseException e) {
            logger.error("RestClientResponseException call-b: " + e.getRawStatusCode() + e.getStatusText() + e.getRawStatusCode());
        } catch (Exception e) {
            logger.error("Exception call-b: " + e);
        }
        return "null";
    }

}
