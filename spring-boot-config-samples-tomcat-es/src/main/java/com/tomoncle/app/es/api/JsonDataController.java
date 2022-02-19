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

package com.tomoncle.app.es.api;

import com.tomoncle.app.es.dao.JsonDataRepository;
import com.tomoncle.app.es.entity.JsonData;
import com.tomoncle.config.springboot.elasticserch.service.ESQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/json")
public class JsonDataController {
    private final ESQueryService<JsonData> queryService;
    private final JsonDataRepository jsonDataRepository;

    public JsonDataController(ESQueryService<JsonData> queryService, JsonDataRepository jsonDataRepository) {
        this.queryService = queryService;
        this.jsonDataRepository = jsonDataRepository;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<JsonData> list() {
        return queryService.page(
                JsonData.INDEX_NAME,
                JsonData.class, "{\"bool\":{\"must\":[{\"match_all\":{}}]}}}",
                PageRequest.of(0, 10));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<JsonData> all() {
        return jsonDataRepository.findAll(PageRequest.of(0, 10)).getContent();
    }


}
