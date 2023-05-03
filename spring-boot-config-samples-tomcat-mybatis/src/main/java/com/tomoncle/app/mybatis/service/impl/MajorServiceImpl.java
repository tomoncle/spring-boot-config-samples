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

package com.tomoncle.app.mybatis.service.impl;


import com.tomoncle.app.mybatis.entity.Major;
import com.tomoncle.app.mybatis.mapper.MajorMapper;
import com.tomoncle.app.mybatis.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by tom.lee on 2017/12/2.
 */
@Service
@Transactional
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<Major> list(String name) {
        return majorMapper.list(name);
    }

    @Override
    public Major get(int id) {
        return majorMapper.get(id);
    }

    @PostConstruct
    public void post() {
        System.out.println("查询：" + this.list(null));
    }
}
