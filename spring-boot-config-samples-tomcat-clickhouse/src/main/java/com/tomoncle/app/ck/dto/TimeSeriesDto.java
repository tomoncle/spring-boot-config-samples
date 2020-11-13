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

package com.tomoncle.app.ck.dto;

import com.tomoncle.app.ck.common.TimeUtils;
import lombok.Data;

import java.util.Random;

/**
 * @author tomoncle
 */
@Data
public class TimeSeriesDto {
    // 时间戳精确到day
    private Long createTime = TimeUtils.days();
    // 时间戳精确到小时
    private Long createTimeHour = TimeUtils.hours();
    // 时间戳精确到分钟
    private Long createTimeMin = TimeUtils.minutes();
    // 时间戳精确到秒
    private Long createTimeSec = TimeUtils.seconds();

    private Float value = new Random().nextFloat();

}
