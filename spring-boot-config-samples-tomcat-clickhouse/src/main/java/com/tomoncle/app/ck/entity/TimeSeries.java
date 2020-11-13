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

package com.tomoncle.app.ck.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author tomoncle
 */
@Data
@Entity
@Table(name = "t_time_series")
public class TimeSeries {

    @Id
    private String uuid;
    // 分区
    private String eventDate;
    // 时间戳精确到day
    private Long createTime;
    // 时间戳精确到小时
    private Long createTimeHour;
    // 时间戳精确到分钟
    private Long createTimeMin;
    // 时间戳精确到秒
    private Long createTimeSec;
    private Float value;
    /*

    DROP table default.time_series ;

    CREATE TABLE default.t_test
    (
        `event_date` Date DEFAULT CAST(now(), 'Date'),
        `uuid` String DEFAULT generateUUIDv4(),
        `create_time` UInt64 DEFAULT toUInt64(toStartOfDay(now(), 'Asia/Hong_Kong')),
        `create_time_hour` Int64 DEFAULT toInt64(toStartOfHour(now(), 'Asia/Hong_Kong')),
        `create_time_min` Int64 DEFAULT toInt64(toStartOfMinute(now(), 'Asia/Hong_Kong')),
        `create_time_sec` Int64 DEFAULT toInt64(now()),
        `value` Float64 DEFAULT 0
    )
    ENGINE = MergeTree(event_date,
     (uuid,
     create_time,
     create_time_hour,
     create_time_min,
     create_time_sec),
     8192)

    insert into default.t_test (value) values (6);
     */
}
