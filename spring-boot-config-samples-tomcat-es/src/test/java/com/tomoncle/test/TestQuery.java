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
import com.tomoncle.app.es.entity.HtmlData;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void aggRepo() {
        TermsAggregationBuilder mb = AggregationBuilders
                .terms("group_by_result").field("result").size(10).order(BucketOrder.count(false));
        // 构建查询条件, 根据result属性模糊查询
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery().must(QueryBuilders.wildcardQuery("result", "*Java*"));
        Aggregations aggregations = repository.aggregations(queryBuilder, mb, HtmlData.class);
        System.out.println(aggregations);
    }

}

