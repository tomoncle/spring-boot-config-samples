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

package com.tomoncle.app.service;

import com.tomoncle.app.entity.Major;
import com.tomoncle.config.springboot.jpa.service.JpaCommonService;

import java.util.List;

public interface MajorService extends JpaCommonService<Major, Integer> {

    List<Major> andAlwaysTrue();

    int deleteNotWhere();

    List<Major> andAlwaysFalse();

    List<Major> likeIsTrue();

    int truncate();

    int dropTable();

    int callProcedure();
}
