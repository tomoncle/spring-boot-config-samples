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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

interface A<T> {
    Class<T> get();
}

interface C extends A<Fake> {
}

/**
 * @author tomoncle
 */
public class TestY {
    public static void main(String[] args) {
        C c = new CC();
        System.out.println(c.get());
    }
}

class Fake {
    @Override
    public String toString() {
        return "Fake class";
    }
}

class AA<T> implements A<T> {
    public Class<T> get() {
        ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        //获得父类上声明的泛型数组
        Type[] actualTypeArguments = superclass.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[0];
    }
}

class CC extends AA<Fake> implements C {
}