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

package com.tomoncle.test;


import com.tomoncle.app.es.Application;
import com.tomoncle.app.es.api.HtmlDataController;
import com.tomoncle.app.es.dao.HtmlDataRepository;
import com.tomoncle.app.es.dto.JsonDto;
import com.tomoncle.app.es.entity.HtmlData;
import com.tomoncle.config.springboot.elasticserch.service.ESQueryService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author tomoncle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestQuery {
    @Autowired
    HtmlDataRepository repository;
    @Autowired
    HtmlDataController htmlDataController;
    @Autowired
    ESQueryService<HtmlData> queryService;
    @Autowired
    ElasticsearchRestTemplate template;


    @Test
    public void like() {
        Page<HtmlData> page = repository.likeSample("Java", PageRequest.of(0, 10));
        assert page.getTotalElements() >= 0L;
    }

    @Test
    public void agg() {
        Map<String, Long> java = htmlDataController.agg("Java");
        assert null != java;
    }

    @Test
    public void aggService() {
        TermsAggregationBuilder mb = AggregationBuilders
                .terms("group_by_result")
                .field("result")
                .size(10)
                .order(BucketOrder.count(false));
        // 构建查询条件, 根据result属性模糊查询
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery().must(QueryBuilders.wildcardQuery("result", "*Java*"));
        Aggregations aggregations = queryService.aggregations(queryBuilder, mb, HtmlData.class);
        assert null != aggregations;
    }

    @Test
    public void scriptQuery() {
        // 查询条件
        String params = "{\"bool\":{\"must\":[{\"match_all\":{}}]}}}";
        //  分页
        PageRequest pageRequest = PageRequest.of(0, 5);
        //  排序
        Sort sort = Sort.by(Sort.Direction.DESC, "result")
                .and(Sort.by(Sort.Direction.ASC, "_id"));
        // 查询返回指定的字段
        String[] fields = {"docId", "result", "404_field_not_return"};
        // 构建查询
        StringQuery query = new StringQuery(params, pageRequest, sort);
        query.addFields(fields);
        // 执行查询
        SearchHits<JsonDto> test_json = template.search(query, JsonDto.class, IndexCoordinates.of("test_es*"));
        List<SearchHit<JsonDto>> searchHits = test_json.getSearchHits();
        for (SearchHit<JsonDto> data : searchHits) {
            System.out.println(data.getId() + " : " + data.getContent());
        }
    }

    @Test
    public void nativeQuery() {
        // 查询条件 "{\"bool\":{\"must\":[{\"match_all\":{}}]}}}";
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
        //  分页
        PageRequest pageRequest = PageRequest.of(0, 5);
        // 查询返回指定的字段
        String[] fields = {"docId", "result"};
        // 构建查询
        NativeSearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withPageable(pageRequest)
                .withSort(SortBuilders.fieldSort("result").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("_id").order(SortOrder.ASC))
                .withFields(fields)
                .build();
        // 执行
        SearchHits<JsonDto> test_json = template.search(query, JsonDto.class, IndexCoordinates.of("test_es7"));
        List<SearchHit<JsonDto>> searchHits = test_json.getSearchHits();
        for (SearchHit<JsonDto> data : searchHits) {
            System.out.println(data.getId() + " : " + data.getContent());
        }
    }
}

