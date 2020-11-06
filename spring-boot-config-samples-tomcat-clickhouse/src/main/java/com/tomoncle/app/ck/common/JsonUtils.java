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

package com.tomoncle.app.ck.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tomoncle
 */
public class JsonUtils {
    public static List<ObjectNode> tupleToJson(List<Tuple> tupleList) {
        List<ObjectNode> json = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Tuple t : tupleList) {
            List<TupleElement<?>> elements = t.getElements();
            ObjectNode node = mapper.createObjectNode();
            for (TupleElement element : elements) {
                switch (element.getJavaType().getName()) {
                    case "java.math.BigInteger":
                        node.put(element.getAlias(), Long.valueOf(t.get(element.getAlias()).toString()));
                        continue;
                    case "java.lang.Integer":
                        node.put(element.getAlias(), Integer.valueOf(t.get(element.getAlias()).toString()));
                        continue;
                    case "java.lang.String":
                    default:
                        node.put(element.getAlias(), t.get(element.getAlias()).toString());
                }
            }
            json.add(node);
        }
        return json;
    }
}
