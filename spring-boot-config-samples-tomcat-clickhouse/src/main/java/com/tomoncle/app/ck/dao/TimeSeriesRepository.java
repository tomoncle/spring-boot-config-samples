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

package com.tomoncle.app.ck.dao;

import com.tomoncle.app.ck.entity.JpaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tomoncle
 */
@Transactional
public interface TimeSeriesRepository extends JpaRepository<JpaModel, String> {


    @Modifying
    @Query(value = "INSERT INTO t_time_series " +
            "(create_date, create_time_hour, create_time_min, create_time_sec, value) " +
            "VALUES " +
            "(:create_date, :create_time_hour, :create_time_min, :create_time_sec, :value_)",
            nativeQuery = true)
    int save(@Param("create_date") Long createDate,
             @Param("create_time_hour") Long createTimeHour,
             @Param("create_time_min") Long createTimeMin,
             @Param("create_time_sec") Long createTimeSec,
             @Param("value_") Float value);


    @Query(value = "SELECT arrayJoin(timeSeriesGroupSum(create_date, create_time_sec, value)) AS rate " +
            "FROM  " +
            "( " +
            "    SELECT  " +
            "        create_date, " +
            "        create_time_sec, " +
            "        value " +
            "    FROM default.t_time_series " +
            "    ORDER BY create_time_sec ASC " +
            ")",
            nativeQuery = true)
    List<String> timeSeriesGroupSumBySec();

    @Query(value = "SELECT toString(arrayJoin(timeSeriesGroupSum(create_date, create_time_min, value))) AS rate " +
            "FROM  " +
            "( " +
            "    SELECT  " +
            "        create_date, " +
            "        create_time_min, " +
            "        value " +
            "    FROM default.t_time_series " +
            "    ORDER BY create_time_min ASC " +
            ")",
            nativeQuery = true)
    List<String> timeSeriesGroupSumByMin();

    @Query(value = "SELECT toString(arrayJoin(timeSeriesGroupSum(create_date, create_time_hour, value))) AS rate " +
            "FROM  " +
            "( " +
            "    SELECT  " +
            "        create_date, " +
            "        create_time_hour, " +
            "        value " +
            "    FROM default.t_time_series " +
            "    ORDER BY create_time_hour ASC " +
            ")",
            nativeQuery = true)
    List<String> timeSeriesGroupSumByHour();


    /**
     * SELECT timeSlots(now(), toUInt32(20), 1) AS dateTimeArray
     *
     * @param startTime 起始时间 2020-11-10 12:03:36
     * @param size      数组长度 = 采点个数 * 步长
     * @param step      步长，秒
     * @return 时间字符串列表
     */
    @Query(value = "SELECT arrayJoin(timeSlots(toDateTime(:startTime, 'Asia/Hong_Kong'), toUInt32(:size_), :step)) as dateTime",
            nativeQuery = true)
    List<java.sql.Timestamp> timeSlots(@Param("startTime") String startTime, @Param("size_") int size, @Param("step") int step);


    /**
     * SELECT timeSlots(now(), toUInt32(20), 1) AS dateTimeArray
     *
     * @param startTime 起始时间 2020-11-10 12:03:36
     * @param size      数组长度 = 采点个数 * 步长
     * @param step      步长，秒
     * @return 时间戳列表
     */
    @Query(value = "SELECT toUnixTimestamp(arrayJoin(timeSlots(toDateTime(:startTime, 'Asia/Hong_Kong'), toUInt32(:size_), :step))) as timestamps",
            nativeQuery = true)
    List<Integer> timestampSlots(@Param("startTime") String startTime, @Param("size_") int size, @Param("step") int step);


}
