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

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author tomoncle
 */
public class TestCase {
    @Test
    public void json() {
        String json = "{\"name\": \"John\", \"age\": 30}";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        String name = JsonPath.read(document, "$.name");
        int age = JsonPath.read(document, "$.age");
        System.out.println("name: " + name + " ; age: " + age);
    }

    @Test
    public void array() {
        String json = "{\"users\": [{\"name\": \"John\", \"age\": 30}, {\"name\": \"Jane\", \"age\": 25}]}";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        // 使用JsonPath表达式查询数组数据：
        // 使用JsonPath表达式$.users[*].name和$.users[*].age分别从JSON数组中提取了所有用户的姓名和年龄。
        // 使用了通配符[*]来表示所有数组元素
        List<String> names = JsonPath.read(document, "$.users[*].name");
        System.out.println(names);

        String name = JsonPath.read(document, "$.users[0].name");
        System.out.println(name);

        // 使用JsonPath表达式$.users[0] 来查询数组第一个元素
        Map<String, Object> user = JsonPath.read(document, "$.users[0]");
        System.out.println(user);

        // 迭代数组：
        // 可以使用JsonPath表达式迭代JSON数组并执行其他操作。
        // 例如，以下示例使用JsonPath表达式迭代JSON数组并计算所有用户的平均年龄：
        List<Integer> ages = JsonPath.read(document, "$.users[*].age");
        double averageAge = ages.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        System.out.println(averageAge);
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
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        // 获取所有年龄大于30岁的用户的姓名。使用JsonPath表达式的条件语句来实现
        // JsonPath表达式$.users[?(@.age > 30)].name来获取所有年龄大于30岁的用户的姓名。
        // 请注意，条件语句[?(@.age > 30)]来判断每个用户的年龄是否大于30岁，并只获取符合条件的用户的姓名。
        // 可以使用其他条件语句来实现更复杂的逻辑，例如逻辑运算符（如AND和OR）和比较运算符（如等于和不等于）。
        List<String> names = JsonPath.read(document, "$.users[?(@.age > 30)].name");
        System.out.println(names);

        // 使用了JsonPath表达式$.users[?(@.age > 30)][0].name来获取所有年龄大于30岁的用户中的第一个姓名
        // 首先使用条件语句[?(@.age > 30)]来筛选出年龄大于30岁的用户，
        // 然后使用索引[0]来获取第一个匹配到的用户，并最后获取该用户的姓名
        names = JsonPath.read(document, "$.users[?(@.age >= 30)]"); // ["John","Bob"]
        System.out.println(names);

        names = JsonPath.read(document, "$.users[?(@.age > 30)]"); // [{"name":"Bob","age":40}]
        System.out.println(names);

        Map<String, Object> user = JsonPath.read(document, "$.users[0]"); // {name=John, age=30}
        System.out.println(user);

        // 获取所有年龄大于30岁的用户的姓名，并在数组为空时返回默认值"jack"，否则返回姓名数组中的第一个值
        names = JsonPath.read(document, "$.users[?(@.age > 30)].name");
        String firstName = names.isEmpty() ? "jack" : names.get(0);
        System.out.println(names);
        System.out.println(firstName);
    }
}
