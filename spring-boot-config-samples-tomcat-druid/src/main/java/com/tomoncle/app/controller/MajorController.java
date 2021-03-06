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

package com.tomoncle.app.controller;

import com.tomoncle.app.entity.Major;
import com.tomoncle.app.service.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "sql防火墙测试", tags = {"sql-firewall-api"})
@RestController
@RequestMapping("/rest/v1/sql")
public class MajorController {

    @Autowired
    MajorService majorService;

    @ApiOperation(value = "禁止AND永真条件查询", notes = "禁止AND永真条件查询", httpMethod = "GET")
    @GetMapping("/andAlwaysTrue")
    public List<Major> andAlwaysTrue() {
        return majorService.andAlwaysTrue();
    }

    @ApiOperation(value = " 禁止DELETE语句无where条件", notes = " 禁止DELETE语句无where条件 ", httpMethod = "DELETE")
    @DeleteMapping("/deleteNotWhere")
    public int deleteNotWhere() {
        return majorService.deleteNotWhere();
    }

    @ApiOperation(value = "禁止call调用", notes = "禁止call调用", httpMethod = "GET")
    @GetMapping("/callProcedure")
    public int callProcedure() {
        return majorService.callProcedure();
    }

    @ApiOperation(value = "禁止AND永假条件查询", notes = "禁止AND永假条件查询", httpMethod = "GET")
    @GetMapping("/andAlwaysFalse")
    public List<Major> andAlwaysFalse() {
        return majorService.andAlwaysFalse();
    }

    @ApiOperation(value = "禁止AND LIKE永真条件查询", notes = "禁止AND LIKE永真条件查询", httpMethod = "GET")
    @GetMapping("/likeIsTrue")
    public List<Major> likeIsTrue() {
        return majorService.likeIsTrue();
    }

    @ApiOperation(value = "禁止truncate语句", notes = "禁止truncate语句", httpMethod = "POST")
    @PostMapping("/truncate")
    public int truncate() {
        return majorService.truncate();
    }

    @ApiOperation(value = "禁止删表", notes = "禁止删表", httpMethod = "DELETE")
    @DeleteMapping("/dropTable")
    public int dropTable() {
        return majorService.dropTable();
    }

}
