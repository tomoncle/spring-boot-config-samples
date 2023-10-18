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

package com.tomoncle.test.jsonpath.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;

/**
 * https://github.com/alibaba/fastjson/wiki/JSONPath
 *
 * @author tomoncle
 */
public class TestCase {

    private String json = "{\"users\": [{\"name\": \"John\", \"age\": 30}, {\"name\": \"Jane\", \"age\": 25}]}";

    @Test
    public void json() {
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray users = (JSONArray) JSONPath.eval(jsonObject, "$.users");
        System.out.println(users); // [{"name":"John","age":30},{"name":"Jane","age":25}]

        JSONArray names = (JSONArray) JSONPath.eval(jsonObject, "$.users.name");
        System.out.println(names); // ["John","Jane"]

        String name = (String) JSONPath.eval(jsonObject, "$.users[1].name");
        System.out.println(name); // Jane
    }

    /**
     * use && and || to combine multiple predicates
     * [?(@.price < 10 && @.category == 'fiction')] , [?(@.category == 'reference' || @.price > 10)].
     * <p>
     * use ! to negate a predicate
     * [?(!(@.price < 10 && @.category == 'fiction'))].
     */
    @Test
    public void array() {
        JSONObject jsonObject = JSON.parseObject(json);

        JSONArray users = (JSONArray) JSONPath.eval(jsonObject, "$.users[?(@.age > 26)]");
        System.out.println("年龄大于26的 user：" + users); // [{"name":"John","age":30}]

        users = (JSONArray) JSONPath.eval(jsonObject, "$.users[?(@.age > 20 && @.age < 30)]");
        System.out.println("年龄大于20并且小于30的 user：" + users); // [{"name":"Jane","age":25}]

        users = (JSONArray) JSONPath.eval(jsonObject, "$.users[?(@.age > 20 && @.name like 'Jo%')]");
        System.out.println("年龄大于20并且name以'Jo'开头的 user：" + users); // [{"name":"John","age":30}]

        JSONObject user = (JSONObject) JSONPath.eval(jsonObject, "$.users[?(@.age > 20)][0]");
        System.out.println("年龄大于20的第一个 user：" + user); // {"name":"John","age":30}

        JSONArray names = (JSONArray) JSONPath.eval(jsonObject, "$.users[?(@.age > 26)].name");
        System.out.println("年龄大于26的 name：" + names); // ["John"]

        names = (JSONArray) JSONPath.eval(jsonObject, "$.users[?(@.age >= 20)].name");
        System.out.println("年龄大于20的 name：" + names); // ["John","Jane"]

        String name = (String) JSONPath.eval(jsonObject, "$.users[?(@.age >= 20)][1].name");
        System.out.println("年龄大于20的第一个name：" + name); // Jane
    }
}
