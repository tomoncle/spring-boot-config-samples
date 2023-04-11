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

package com.tomoncle.app.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tomoncle
 */
public class Slf4jApp {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jApp.class);

    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    logger.trace("trace");
                    logger.debug("debug");
                    logger.info("info");
                    logger.warn("warn");
                    logger.error("error");
                }
            }).start();
        }
    }
}
