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

package com.tomoncle.test.jsonpath;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * https://github.com/json-path/JsonPath
 * @author tomoncle
 */
public class TestCase {
    @Test
    public void json() {
        String json = "{\"name\": \"John\", \"age\": 30}";

        ReadContext ctx = JsonPath.parse(json);
        String name = ctx.read("$.name");
        int age = ctx.read("$.age");

        System.out.println("name: " + name + " ; age: " + age);

        json = "[\"jack\",\"tom\"]";
        ctx = JsonPath.parse(json);
        System.out.println(ctx.jsonString() + " - " + ctx.read("$.[0]"));
    }

    @Test
    public void array() {
        String json = "{\"users\": [{\"name\": \"John\", \"age\": 30}, {\"name\": \"Jane\", \"age\": 25}]}";
        ReadContext ctx = JsonPath.parse(json);
        // 使用JsonPath表达式查询数组数据：
        // 使用JsonPath表达式$.users[*].name和$.users[*].age分别从JSON数组中提取了所有用户的姓名和年龄。
        // 使用了通配符[*]来表示所有数组元素
        List<String> names = ctx.read("$.users[*].name");
        System.out.println(names); // ["John","Jane"]

        String name = ctx.read("$.users[0].name");
        System.out.println(name); // John

        // 使用JsonPath表达式$.users[0] 来查询数组第一个元素
        Map<String, Object> user = ctx.read("$.users[0]");
        System.out.println(user); // {name=John, age=30}

        // 迭代数组：
        // 可以使用JsonPath表达式迭代JSON数组并执行其他操作。
        // 例如，以下示例使用JsonPath表达式迭代JSON数组并计算所有用户的平均年龄：
        List<Integer> ages = ctx.read("$.users[*].age");
        double averageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        System.out.println(averageAge); // 27.5
    }

    @Test
    public void arrayAndCompare() {
        String json = "{\n" +
                "  \"users\": [\n" +
                "    {\n" +
                "      \"name\": \"John\",\n" +
                "      \"age\": 30\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Jane\",\n" +
                "      \"age\": 25\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"Bob\",\n" +
                "      \"age\": 40\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        ReadContext ctx = JsonPath.parse(json);

        // 获取所有年龄大于30岁的用户的姓名。使用JsonPath表达式的条件语句来实现
        // JsonPath表达式$.users[?(@.age > 30)].name来获取所有年龄大于30岁的用户的姓名。
        // 请注意，条件语句[?(@.age > 30)]来判断每个用户的年龄是否大于30岁，并只获取符合条件的用户的姓名。
        // 可以使用其他条件语句来实现更复杂的逻辑，例如逻辑运算符（如AND和OR）和比较运算符（如等于和不等于）。
        List<String> names = ctx.read("$.users[?(@.age > 30)].name");
        System.out.println(names); // ["Bob"]

        // JsonPath表达式不支持使用表达式后提取数组下标，如：$.users[?(@.age > 30)][0]
        // https://github.com/json-path/JsonPath/issues/272, 可以使用 fastjson 实现，fastjson支持该语法
        names = ctx.read("$.users[?(@.age >= 30)][0]"); // 这个表达式现在不支持
        System.out.println(names); // []

        // 获取所有年龄大于30岁的用户
        List<String> users = ctx.read("$.users[?(@.age > 30)]");
        System.out.println(users); // [{"name":"Bob","age":40}]

        // 获取第一个用户
        Map<String, Object> user = ctx.read("$.users[0]");
        System.out.println(user); // {name=John, age=30}

        // 获取所有年龄大于30岁的用户中的第一个姓名。由于JsonPath不支持表达式后直接使用数组下标，只能使用Java代码处理
        // 使用JsonPath表达式$.users[?(@.age > 30)].name来获取所有年龄大于30岁的用户的姓名，并使用Java代码来处理结果
        names = ctx.read("$.users[?(@.age > 30)].name");
        String firstName = names.isEmpty() ? null : names.get(0);
        System.out.println(firstName); // Bob

        // 获取所有年龄大于50岁的用户的姓名，并在数组为空时返回默认值"jack"，否则返回姓名数组中的第一个值
        names = ctx.read("$.users[?(@.age > 50)].name");
        firstName = names.isEmpty() ? "jack" : names.get(0);
        System.out.println(names); // []
        System.out.println(firstName); // jack
    }
}
