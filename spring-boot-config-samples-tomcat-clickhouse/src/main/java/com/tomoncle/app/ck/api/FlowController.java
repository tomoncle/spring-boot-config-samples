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

package com.tomoncle.app.ck.api;

import com.alibaba.fastjson.JSONObject;
import com.tomoncle.app.ck.dao.FlowRepository;
import com.tomoncle.app.ck.dao.SqlRepository;
import com.tomoncle.config.springboot.utils.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/flow")
public class FlowController {

    private final FlowRepository flowRepository;
    private final SqlRepository sqlRepository;

    public FlowController(FlowRepository flowRepository, SqlRepository sqlRepository) {
        this.flowRepository = flowRepository;
        this.sqlRepository = sqlRepository;
    }

    @GetMapping("queryFlowPackRate")
    public List<JSONObject> queryFlowPackRate(
            @RequestParam(value = "startTime", defaultValue = "", required = false) String startTime,
            @RequestParam(value = "size", defaultValue = "300") int size,
            @RequestParam(value = "step", defaultValue = "1") int step,
            @RequestParam(value = "ipAddr") String ipAddr) {
        if (StringUtils.isEmpty(startTime)) {
            startTime = DateUtils.format(DateUtils.shanghaiZonedNowDateTime().minusSeconds(size * step));
        }
        return flowRepository.queryFlowPackRate(startTime, size * step, step, ipAddr);
    }

    @GetMapping("queryComplexFlowPackRate")
    public List<Map<String, Object>> queryFlowPackRate(
            @RequestParam(value = "startTime", defaultValue = "", required = false) String startTime,
            @RequestParam(value = "size", defaultValue = "300") int size,
            @RequestParam(value = "step", defaultValue = "1") int step,
            @RequestParam(value = "ipv4SrcAddr", defaultValue = "", required = false) String ipv4SrcAddr,
            @RequestParam(value = "ipv4DstAddr", defaultValue = "", required = false) String ipv4DstAddr,
            @RequestParam(value = "ipv4Addr", defaultValue = "", required = false) String ipv4Addr,
            @RequestParam(value = "ipv6SrcAddr", defaultValue = "", required = false) String ipv6SrcAddr,
            @RequestParam(value = "ipv6DstAddr", defaultValue = "", required = false) String ipv6DstAddr,
            @RequestParam(value = "ipv6Addr", defaultValue = "", required = false) String ipv6Addr) {
        if (StringUtils.isEmpty(startTime)) {
            startTime = DateUtils.format(DateUtils.shanghaiZonedNowDateTime().minusSeconds(size * step));
        }
        String sql = "SELECT " +
                "     t_virtual_table.create_time as cst_timestamp, " +
                "     SUM(t_flow.IN_PKTS) as total " +
                " FROM t_flow " +
                " right join (" +
                "     SELECT toInt64(toUnixTimestamp(arrayJoin(timeSlots(toDateTime('" +
                startTime + "','Asia/Hong_Kong'), toUInt32(" + size * step + ")," +
                step + ")))) as create_time ) as t_virtual_table " +
                " ON t_virtual_table.create_time == t_flow.create_time_sec " +
                " WHERE 1==1 " + buildCondition(ipv4SrcAddr, ipv4DstAddr, ipv4Addr, ipv6SrcAddr, ipv6DstAddr, ipv6Addr) +
                " GROUP BY t_virtual_table.create_time " +
                " ORDER BY t_virtual_table.create_time ASC";
        return sqlRepository.queryForMaps(sql);
    }

    private String buildCondition(String ipv4SrcAddr, String ipv4DstAddr, String ipv4Addr,
                                  String ipv6SrcAddr, String ipv6DstAddr, String ipv6Addr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!StringUtils.isEmpty(ipv4Addr)) {
            stringBuilder.append(" or (t_flow.IPV4_SRC_ADDR == '")
                    .append(ipv4Addr).append("' or t_flow.IPV4_DST_ADDR == '").append(ipv4Addr)
                    .append("') ");
        } else {
            if (!StringUtils.isEmpty(ipv4SrcAddr)) {
                stringBuilder.append(" or t_flow.IPV4_SRC_ADDR == '").append(ipv4SrcAddr).append("' ");
            }
            if (!StringUtils.isEmpty(ipv4DstAddr)) {
                stringBuilder.append(" or t_flow.IPV4_DST_ADDR == '").append(ipv4DstAddr).append("' ");
            }
        }
        if (!StringUtils.isEmpty(ipv6Addr)) {
            stringBuilder.append(" or (t_flow.IPV6_SRC_ADDR == '")
                    .append(ipv6Addr).append("' or t_flow.IPV6_DST_ADDR == '").append(ipv6Addr)
                    .append("') ");
        } else {
            if (!StringUtils.isEmpty(ipv6SrcAddr)) {
                stringBuilder.append(" or t_flow.IPV6_SRC_ADDR == '").append(ipv6SrcAddr).append("' ");
            }
            if (!StringUtils.isEmpty(ipv6DstAddr)) {
                stringBuilder.append(" or t_flow.IPV6_DST_ADDR == '").append(ipv6DstAddr).append("' ");
            }
        }
        return stringBuilder.toString();
    }
}
