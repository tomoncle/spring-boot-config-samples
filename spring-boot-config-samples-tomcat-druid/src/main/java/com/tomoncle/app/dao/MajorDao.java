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

package com.tomoncle.app.dao;

import com.tomoncle.app.entity.Major;
import com.tomoncle.config.springboot.jpa.repository.JpaCommonRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorDao extends JpaCommonRepository<Major, Integer> {

    @Query("select id, name, description from Major where 1 = 1 and 2 = 2 ")
    List<Major> andAlwaysTrue();

    @Query("select id, name, description from Major where 1 = 2 and 0 = 2 ")
    List<Major> andAlwaysFalse();

    @Query("select id, name, description from Major where id is not null and name like '%'")
    List<Major> likeIsTrue();

    @Modifying
    @Query(value = "delete from Major", nativeQuery = true)
    int deleteNotWhere();

    @Modifying
    @Query(value = "drop table User", nativeQuery = true)
    void dropTable();

    @Modifying
    @Query(value = "truncate table User", nativeQuery = true)
    void truncate();

    @Procedure(procedureName = "majorCount", outputParameterName = "major_count")
    int callProcedure();
}
