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

import com.tomoncle.config.springboot.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author tomoncle
 */
public class TimeUtils {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Long days() {
        return parse(DateUtils.shanghaiZonedNowDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " 00:00:00");
    }

    public static Long hours() {
        return parse(DateUtils.shanghaiZonedNowDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")) + ":00:00");
    }

    public static Long minutes() {
        return parse(DateUtils.shanghaiZonedNowDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ":00");
    }

    public static Long seconds() {
        return parse(DateUtils.shanghaiZonedNowDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static Long parse(String dateString) {
        try {
            return SIMPLE_DATE_FORMAT.parse(dateString).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;

    }
}
