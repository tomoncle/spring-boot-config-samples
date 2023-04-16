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

package com.tomoncle.test.app.web.jpa;

import com.tomoncle.config.springboot.utils.http.OkHttpRequest;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author tomoncle
 */
public class TestUserBatchSave {

    private static Logger logger = LogManager.getLogger(TestUserBatchSave.class);

    private AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(300);
        pool.setCorePoolSize(100);
        pool.setMaxPoolSize(100);
        pool.setQueueCapacity(1000);
        pool.setThreadNamePrefix("tomoncle.app.");
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.initialize();
        return pool;
    }

    @Test
    public void save() throws InterruptedException, IOException {
        // 插入的起始值
        int begin = 2000;
        int size = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AsyncTaskExecutor executor = asyncTaskExecutor();
        String url = "http://localhost:8080/users";
        OkHttpRequest.DELETE.request(url);
        long time = System.currentTimeMillis();
        for (int i = begin; i < begin + size; i++) {
            final int index = i;
            executor.execute(() -> {
                OkHttpRequest.BodyMap map = new OkHttpRequest.BodyMap();
                map.put("username", "tomoncle" + index);
                map.put("password", "123456");
                map.put("enabled", "true");
                map.put("locked", "true");
                try {
                    OkHttpRequest.POST.request(url, map);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        logger.info((System.currentTimeMillis() - time) / 1000.0 + "/S");
    }


    @Test
    public void query() throws InterruptedException, IOException {
        int size = 3000;
        CountDownLatch countDownLatch = new CountDownLatch(size);
        AsyncTaskExecutor executor = asyncTaskExecutor();
        String url = "http://localhost:8080/healthy";
        long time = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            executor.execute(() -> {
                try {
                    Response response = OkHttpRequest.GET.response(url);
                    if (response.code() != 200) {
                        logger.error(response.message());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        logger.info((System.currentTimeMillis() - time) / 1000.0 + "/S");
    }
}
