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

package com.tomoncle.test.nacos;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tomoncle.config.springboot.utils.http.OkHttpRequest;
import okhttp3.Headers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author tomoncle
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
        ExecutorService threadPool = new ThreadPoolExecutor(20, 32, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            threadPool.execute(() -> {
                String url = "http://localhost:8013/web-c/call-b";
                if (finalI % 2 == 0) {
                    url = "http://localhost:8013/web-c/call-a";
                }
                Map<String, String> map = new HashMap<>();
                map.put("token", "123456");
                String response = null;
                try {
                    response = OkHttpRequest.GET.request(url, Headers.of(map));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " : " + response);
            });
        }
        threadPool.shutdown();
    }

}
