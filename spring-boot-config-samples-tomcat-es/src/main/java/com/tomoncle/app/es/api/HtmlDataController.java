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

import com.tomoncle.app.es.dao.HtmlDataRepository;
import com.tomoncle.app.es.entity.HtmlData;
import com.tomoncle.config.springboot.utils.GenUUID;
import com.tomoncle.config.springboot.utils.HashUtils;
import lombok.SneakyThrows;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/api")
public class HtmlDataController {

    private static final Logger logger = LoggerFactory.getLogger(HtmlDataController.class);
    private final HtmlDataRepository repository;

    public HtmlDataController(HtmlDataRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/import", method = RequestMethod.HEAD)
    public int importDataFromMySQL() {
        Runnable runnable = this::batchImport;
        new Thread(runnable).start();
        return 0;
    }

    @RequestMapping(value = "/list")
    public List<HtmlData> list(@RequestParam String word,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        page = page > 0 ? page - 1 : 0;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HtmlData> byResult = repository.querySample(word, pageRequest);
        return byResult.getContent();
    }

    @RequestMapping(value = "/like")
    public List<HtmlData> like(@RequestParam String word,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        page = page > 0 ? page - 1 : 0;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<HtmlData> byResult = repository.likeSample(word, pageRequest);
        return byResult.getContent();
    }

    @RequestMapping(value = "/sort")
    public List<HtmlData> sort(@RequestParam String word,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "-1") int order) {
        page = page > 0 ? page - 1 : 0;
        Sort orderId = Sort.by(order > 0 ? Sort.Direction.DESC : Sort.Direction.ASC, "_id");
        Sort orderResult = Sort.by(order < 0 ? Sort.Direction.DESC : Sort.Direction.ASC, "result");
        PageRequest pageRequest = PageRequest.of(page, size)
                .withSort(orderId)
                .withSort(orderResult);
        Page<HtmlData> byResult = repository.sortSample(word, pageRequest);
        return byResult.getContent();
    }

    @RequestMapping(value = "/clear", method = RequestMethod.DELETE)
    public Long clear() {
        Long count = repository.count();
        repository.deleteAll();
        return count;
    }

    @RequestMapping(value = "/agg")
    public Map<String, Long> agg(@RequestParam String word) {
        // 构建聚合查询条件，根据result属性进行分组，并根据统计值进行倒序排序
        TermsAggregationBuilder mb = AggregationBuilders
                .terms("group_by_result").field("result").size(10).order(BucketOrder.count(false));
        // 构建查询条件, 根据result属性模糊查询
        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery().must(QueryBuilders.wildcardQuery("result", "*" + word + "*"));

        Aggregations aggregations = repository.aggregations(queryBuilder, mb, HtmlData.class);
        Map<String, Long> map = new LinkedHashMap<>();
        if (null == aggregations) {
            return map;
        }
        List<Aggregation> aggregationsList = aggregations.asList();
        for (Aggregation aggregation : aggregationsList) {
            logger.info("aggregation: {}", aggregation.getName());
            List<? extends Terms.Bucket> buckets = ((ParsedStringTerms) aggregation).getBuckets();
            for (Terms.Bucket bucket : buckets) {
                map.put(bucket.getKeyAsString(), bucket.getDocCount());
            }
        }
        return map;
    }

    @SneakyThrows
    private void batchImport() {
        repository.saveAll(getData());
        Thread.sleep(1000);
    }

    private List<HtmlData> getData() {
        List<HtmlData> htmlData = new ArrayList<>();
        HtmlData analysisResultHistory;
        GenUUID uuid = GenUUID.build();
        Random random = new Random();
        String urlPrefix = "www.google.com/api/";
        String[] words = {
                "Java导航", "Java电影", "Java动画", "Java社区", "Java视频", "Java文档",
                "Python导航", "Python电影", "Python动画",
                "Go社区", "Go视频", "Go文档"
        };
        String id, url, docId, result;
        for (int i = 0; i < 101; i++) {
            id = uuid.generate();
            url = urlPrefix + id;
            docId = HashUtils.fingerprint(url);
            result = words[random.nextInt(words.length)];
            analysisResultHistory = new HtmlData();
            analysisResultHistory.setId(uuid.generate());
            analysisResultHistory.setDocId(docId);
            analysisResultHistory.setUrl(url);
            analysisResultHistory.setResult(result);
            analysisResultHistory.setStatus(2);
            analysisResultHistory.setCreateTimestamp(System.currentTimeMillis());
            analysisResultHistory.setUpdateTimestamp(System.currentTimeMillis());
            htmlData.add(analysisResultHistory);
        }
        return htmlData;
    }
}
