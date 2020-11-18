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

import com.alibaba.fastjson.JSONObject;
import com.tomoncle.app.ck.entity.JpaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author tomoncle
 */
public interface FlowRepository extends JpaRepository<JpaModel, String> {

    /**
     * 计算连续的收包速率
     *
     * @param startTime 起始时间 2020-11-10 12:03:36
     * @param size      数组长度 = 采点个数 * 步长
     * @param step      步长，秒
     * @return List<JSONObject>
     */
    @Query(value = "SELECT " +
            "   t_virtual_table.create_time as cst_timestamp, " +
            "   SUM(t_flow.IN_PKTS) as total " +
            "FROM " +
            "   t_flow " +
            "right join ( " +
            "    SELECT toInt64(toUnixTimestamp(arrayJoin(" +
            "         timeSlots(toDateTime(:startTime, 'Asia/Hong_Kong'), toUInt32(:size_), :step)))) as create_time " +
            "    ) as t_virtual_table " +
            "ON  " +
            "   t_virtual_table.create_time == t_flow.create_time_sec " +
            "WHERE " +
            "   t_flow.IPV4_SRC_ADDR == :ipAddr " +
            "GROUP BY " +
            "   t_virtual_table.create_time " +
            "ORDER BY " +
            "   t_virtual_table.create_time ASC", nativeQuery = true)
    List<JSONObject> queryFlowPackRate(@Param("startTime") String startTime,
                                       @Param("size_") int size,
                                       @Param("step") int step,
                                       @Param("ipAddr") String ipAddr);


    /**
     * 获取4层流量协议分布
     *
     * @return List<JSONObject>
     */
    @Query(value = "select " +
            "count(PROTOCOL_MAP) as size, " +
            "PROTOCOL_MAP as L4_PROTO_NAME " +
            "from " +
            "t_flow " +
            "group by " +
            "PROTOCOL_MAP", nativeQuery = true)
    List<JSONObject> protocolRatioL4();


    /**
     * 获取7层协议分布
     * <p>
     * SELECT
     * L7_PROTO_NAME,
     * count(L7_PROTO_NAME) as records
     * from
     * db_npm.t_flow
     * where
     * L7_PROTO_NAME != ''
     * group by
     * L7_PROTO_NAME
     * order by
     * records desc
     * <p>
     * <p>
     * SELECT
     * PROTO_NAME,
     * count(PROTO_NAME) as records
     * from
     * (
     * select
     * L7_PROTO_NAME,
     * if(length(L7_PROTO_NAME)== 0,'Unknown',L7_PROTO_NAME ) AS PROTO_NAME
     * from
     * db_npm.t_flow
     * )
     * group by
     * PROTO_NAME
     * order by
     * records desc
     *
     * @return List<JSONObject>
     */
    @Query(value = "SELECT " +
            "L7_PROTO_NAME, " +
            "count(L7_PROTO_NAME) as size " +
            "from " +
            "t_flow " +
            "group by L7_PROTO_NAME " +
            "order by size desc", nativeQuery = true)
    List<JSONObject> protocolRatioL7();



}
