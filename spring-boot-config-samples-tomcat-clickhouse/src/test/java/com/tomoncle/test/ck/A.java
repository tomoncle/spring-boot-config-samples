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

package com.tomoncle.test.ck;

/**
 * @author tomoncle
 */
public class A {
    public static void main(String[] args) {
        String[] strings = "jdbc:clickhouse://127.0.0.1:8123/default?characterEncoding=utf8".split("/");
        System.out.println(strings[2]);
        System.out.println(strings[3].split("\\?")[0]);
    }
}
