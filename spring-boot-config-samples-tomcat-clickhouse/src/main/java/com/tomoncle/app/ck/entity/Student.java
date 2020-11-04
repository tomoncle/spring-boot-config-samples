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
@Table(name = "t_student")
public class Student {
    @Id
    private Long userId = System.currentTimeMillis();
    private String name;
    private Integer age;
    private String eventDate;

    /*
    CREATE TABLE default.t_student
    (
     `user_id` UInt64,
     `name` String,
     `age` Int32,
     `event_date` Date DEFAULT CAST(now(),'Date')
    )
    ENGINE = MergeTree(event_date, intHash32(user_id), 8192);
     */

}
