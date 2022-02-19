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
public class TestZ<S, T> {
    public static void main(String[] args) {
        TestZ<M, N> testZ = new TestZ<>();
        M m = new M();
        m.setId("abc");
        System.out.println(testZ.copyTarget(m, N.class));
    }

    public List<T> copyTarget(List<S> sources, Class<? extends T> target) {
        List<T> targets = new ArrayList<>();
        T T;
        for (S S : sources) {
            try {
                T = target.newInstance();
                BeanUtils.copyProperties(S, T);
                targets.add(T);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return targets;
    }

    public List<S> copySource(List<T> sources, Class<? extends S> target) {
        List<S> targets = new ArrayList<>();
        S t;
        for (T s : sources) {
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

    public T copyTarget(S source, Class<? extends T> target) {
        try {
            T T = target.newInstance();
            BeanUtils.copyProperties(source, T);
            return T;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public S copySource(T source, Class<? extends S> target) {
        try {
            S t = target.newInstance();
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
