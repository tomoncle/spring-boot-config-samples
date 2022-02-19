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

package com.tomoncle.app.es.dao;

import com.tomoncle.app.es.entity.HtmlData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tomoncle
 * @apiNote https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.query-methods.criterions
 */
@Repository
public interface HtmlDataRepository extends ElasticsearchRepository<HtmlData, String> {
    @Query("{\"match\":{\"result\":{\"query\":\"?0\"}}}")
    Page<HtmlData> querySample(String name, Pageable pageable);

    @Query("{\"bool\":{\"must_not\":[],\"should\":[],\"must\":[{\"wildcard\":{\"result\":\"*?0*\"}}]}}")
    Page<HtmlData> likeSample(String name, Pageable pageable);

    @Query("{\"bool\":{\"must\":[{\"wildcard\":{\"result\":\"*?0*\"}}]}}")
    Page<HtmlData> sortSample(String name, Pageable pageable);
}
