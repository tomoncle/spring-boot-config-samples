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

package com.tomoncle.app.fm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.stream.Stream;

/**
 * @author tomoncle
 */
public abstract class PrettyFileUtils {
    private final static PrettyFileUtils PRETTY_FILE_UTILS = PrettyFileUtils.getAnonymousInstance();


    private PrettyFileUtils() {
    }

    private static PrettyFileUtils getAnonymousInstance() {
        return new PrettyFileUtils() {
        };
    }

    public static String read(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                PRETTY_FILE_UTILS.getClass().getResourceAsStream(path)))) {
            Stream<String> lines = bufferedReader.lines();
            lines.forEach(s -> stringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        }
        return stringBuilder.toString();
    }

    public static Reader reader(String path) {
        return new BufferedReader(new InputStreamReader(PRETTY_FILE_UTILS.getClass().getResourceAsStream(path)));
    }
}
