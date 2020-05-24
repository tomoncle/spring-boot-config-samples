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

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomoncle
 */
public class TestZ<SOURCE, TARGET> {
    public static void main(String[] args) {
        TestZ<M, N> testZ = new TestZ<>();
        M m = new M();
        m.setId("abc");
        System.out.println(testZ.copyTarget(m, N.class));
    }

    public List<TARGET> copyTarget(List<SOURCE> sources, Class<? extends TARGET> target) {
        List<TARGET> targets = new ArrayList<>();
        TARGET TARGET;
        for (SOURCE SOURCE : sources) {
            try {
                TARGET = target.newInstance();
                BeanUtils.copyProperties(SOURCE, TARGET);
                targets.add(TARGET);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targets;
    }

    public List<SOURCE> copySource(List<TARGET> sources, Class<? extends SOURCE> target) {
        List<SOURCE> targets = new ArrayList<>();
        SOURCE t;
        for (TARGET s : sources) {
            try {
                t = target.newInstance();
                BeanUtils.copyProperties(s, t);
                targets.add(t);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targets;
    }

    public TARGET copyTarget(SOURCE source, Class<? extends TARGET> target) {
        try {
            TARGET TARGET = target.newInstance();
            BeanUtils.copyProperties(source, TARGET);
            return TARGET;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SOURCE copySource(TARGET source, Class<? extends SOURCE> target) {
        try {
            SOURCE t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    static class M {
        private String id;
    }

    @Data
    static class N {
        private String id;
    }
}
