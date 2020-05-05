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

package com.tomoncle.app.service.impl;

import com.tomoncle.app.dao.MajorDao;
import com.tomoncle.app.entity.Major;
import com.tomoncle.app.service.MajorService;
import com.tomoncle.config.springboot.jpa.service.impl.JpaCommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class MajorServiceImpl extends JpaCommonServiceImpl<Major, Integer> implements MajorService {

    @Autowired
    MajorDao majorDao;

    @Override
    public List<Major> andAlwaysTrue() {
        return majorDao.andAlwaysTrue();
    }

    @Transactional
    @Override
    public int deleteNotWhere() {
        return majorDao.deleteNotWhere();
    }

    @Override
    public List<Major> andAlwaysFalse() {
        return majorDao.andAlwaysFalse();
    }

    @Override
    public List<Major> likeIsTrue() {
        return majorDao.likeIsTrue();
    }

    @Transactional
    @Override
    public int truncate() {
        majorDao.truncate();
        return 1;
    }

    @Transactional
    @Override
    public int dropTable() {
        majorDao.dropTable();
        return 1;
    }

    @Override
    public int callProcedure() {
        return majorDao.callProcedure();
    }
}
