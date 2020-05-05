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
import com.tomoncle.app.es.dao.HtmlDataRepository;
import com.tomoncle.app.es.dao.HtmlDataUnblockRepository;
import com.tomoncle.app.es.entity.HtmlData;
import com.tomoncle.app.es.entity.HtmlDataUnblock;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomoncle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestESImportMySQLData {

    private static final Logger logger = LoggerFactory.getLogger(TestESImportMySQLData.class);

    @Autowired
    HtmlDataRepository htmlDataRepository;

    @Autowired
    HtmlDataUnblockRepository analysisResultHistoryRepository;

    @Test
    public void readDBSize() {
        logger.info("readDBSize, {}", htmlDataRepository.count());
    }

    @SneakyThrows
    @Test
    public void importDataFromMySQL() {
        final int offset = 10000;
        final Integer total = Integer.valueOf(htmlDataRepository.count() + "");
        for (int i = 0; i <= total / offset; i++) {
            List<HtmlData> dataList = htmlDataRepository.queryBatch(i * offset, offset);
            List<HtmlDataUnblock> analysisResultHistoryList = copy(dataList);
            analysisResultHistoryRepository.saveAll(analysisResultHistoryList);
            analysisResultHistoryRepository.refresh();
//            htmlDataRepository.deleteInBatch(dataList);
            Thread.sleep(1000);
        }


    }


    public List<HtmlDataUnblock> copy(List<HtmlData> dataList) {
        List<HtmlDataUnblock> analysisResultHistoryList = new ArrayList<>();
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
            analysisResultHistoryList.add(analysisResultHistory);
        }
        return analysisResultHistoryList;
    }


}
