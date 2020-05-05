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

package com.tomoncle.app.config;

import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DruidWallFilterConfiguration {
    //    private boolean selelctAllow;
    private static final boolean callAllow = false;
    private static final boolean conditionAndAlwayFalseAllow = false;
    private static final boolean conditionAndAlwayTrueAllow = false;
    private static final boolean conditionLikeTrueAllow = false;
    private static final boolean deleteWhereNoneCheck = true;
    private static final boolean truncateAllow = false;
    private static final boolean dropTableAllow = false;

    /**
     * 配置防火墙规则
     * docs: https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
     * <p/>
     * 禁止 call调用                  callAllow=true                      =>false
     * 禁止 AND 永假条件查询           conditionAndAlwayFalseAllow=false   =>false
     * 禁止 AND 永真条件查询           conditionAndAlwayTrueAllow=false    =>false
     * 禁止 AND LIKE 永真条件查询      conditionLikeTrueAllow=true         =>false
     * 禁止 DELETE语句无where条件      deleteWhereNoneCheck=false          =>true
     * 禁止 truncate语句              truncateAllow=true                  =>false
     * 禁止 删表                      dropTableAllow=true                 =>false
     */
    @Bean
    public WallConfig wallConfig() {
        WallConfig wc = new WallConfig();
        wc.setCallAllow(callAllow);
        wc.setConditionAndAlwayTrueAllow(conditionAndAlwayTrueAllow);
        wc.setConditionAndAlwayFalseAllow(conditionAndAlwayFalseAllow);
        wc.setConditionLikeTrueAllow(conditionLikeTrueAllow);
        wc.setDeleteWhereNoneCheck(deleteWhereNoneCheck);
        wc.setTruncateAllow(truncateAllow);
        wc.setDropTableAllow(dropTableAllow);
        return wc;
    }

    /**
     * 自定义配置的防火墙:
     * 这里的name = "wallFilter"在配置文件中配置如下:
     * druid.datasource.proxyFilters=wallFilter
     * druid.datasource.filters=stat,slf4j
     * <p>
     * 默认的防火墙配置是,只配置filters:
     * 　　　　druid.datasource.filters=wall,stat,slf4j
     *
     * @param wallConfig 　防火墙配置
     * @return WallFilter
     */
    @Bean(name = "wallFilter")
    public WallFilter wallFilter(WallConfig wallConfig) {
        WallFilter wf = new WallFilter();
        wf.setConfig(wallConfig);
        return wf;
    }
}
