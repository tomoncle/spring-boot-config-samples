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
import com.tomoncle.app.es.dao.HtmlDataUnblockRepository;
import com.tomoncle.app.es.entity.HtmlData;
import com.tomoncle.app.es.entity.HtmlDataUnblock;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/import")
public class HtmlDataController {

    private static final Logger logger = LoggerFactory.getLogger(HtmlDataController.class);

    private final HtmlDataRepository htmlDataRepository;
    private final HtmlDataUnblockRepository analysisResultHistoryRepository;
    private final StringRedisTemplate stringRedisTemplate;

    public HtmlDataController(HtmlDataRepository htmlDataRepository,
                              HtmlDataUnblockRepository analysisResultHistoryRepository,
                              StringRedisTemplate stringRedisTemplate) {
        this.htmlDataRepository = htmlDataRepository;
        this.analysisResultHistoryRepository = analysisResultHistoryRepository;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @RequestMapping(value = "/start", method = RequestMethod.HEAD)
    public int importDataFromMySQL() {
        Runnable runnable = this::batchImport;
        new Thread(runnable).start();
        return 0;
    }

    @SneakyThrows
    private void batchImport() {
        final String lockName = "import_task_page_index";
        final int offset = 10000;
        final Integer total = Integer.valueOf(htmlDataRepository.count() + "");
        for (int i = 0; i <= total / offset; i++) {
            String index = stringRedisTemplate.opsForValue().get(lockName);
            if (null != index && i <= Integer.valueOf(index)) {
                continue;
            }
            long start = System.currentTimeMillis();
            analysisResultHistoryRepository.saveAll(copy(htmlDataRepository.queryBatch(i * offset, offset)));
            analysisResultHistoryRepository.refresh();
            logger.info("处理：{}~{} 条数据，耗时 {} s.",
                    i * offset,
                    (i + 1) * offset,
                    (System.currentTimeMillis() - start) / 1000.0);
            stringRedisTemplate.opsForValue().set(lockName, "" + i);
            Thread.sleep(1000);
        }
    }

    private List<HtmlDataUnblock> copy(List<HtmlData> dataList) {
        List<HtmlDataUnblock> htmlDataUnblocks = new ArrayList<>();
        HtmlDataUnblock analysisResultHistory;
        for (HtmlData htmlData : dataList) {
            analysisResultHistory = new HtmlDataUnblock();
            analysisResultHistory.setId(htmlData.getId());
            analysisResultHistory.setDocId(htmlData.getDocId());
            analysisResultHistory.setUrl(htmlData.getUrl());
            analysisResultHistory.setResult(htmlData.getResult());
            analysisResultHistory.setStatus(htmlData.getStatus());
            analysisResultHistory.setCreateTimestamp(htmlData.getCreateTime().getTime());
            analysisResultHistory.setUpdateTimestamp(htmlData.getUpdateTime().getTime());
            htmlDataUnblocks.add(analysisResultHistory);
        }
        return htmlDataUnblocks;
    }
}
