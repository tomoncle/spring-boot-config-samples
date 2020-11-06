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


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tomoncle.app.ck.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;

/**
 * @author tomoncle
 * clickhouse 不能直接使用jpa的部分法，因为某些属性的类型，并没有映射为数据库的类型
 * 所以建议使用原生SQL
 */
@Transactional
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * 保存, 其中“event_date”使用内置函数 CAST(now(), 'Date')
     *
     * @param userId userId
     * @param name   name
     * @param age    age
     * @return status
     */
    @Modifying
    @Query(value = "INSERT INTO t_student " +
            "(user_id, name, age, event_date) " +
            "VALUES " +
            "(:userId, :name_, :age, CAST(now(), 'Date'))",
            nativeQuery = true)
    int save(@Param("userId") Long userId, @Param("name_") String name, @Param("age") Integer age);

    /**
     * 更新
     *
     * @param userId userId
     * @param name   name
     * @param age    age
     * @return status
     */
    @Modifying
    @Query(value = "ALTER TABLE t_student " +
            "UPDATE " +
            "age=:age, " +
            "name=:name_ " +
            "where " +
            "user_id =:userId",
            nativeQuery = true)
    int update(@Param("userId") Long userId, @Param("name_") String name, @Param("age") Integer age);

    /**
     * 删除
     *
     * @param userId userId
     */
    @Modifying
    @Query(value = "ALTER TABLE t_student " +
            "DELETE " +
            "where " +
            "user_id =:userId",
            nativeQuery = true)
    void delete(@Param("userId") Long userId);

    /**
     * 查询数组
     *
     * @return [[1604465665858,"jackson",100],[10,"tomoncle",20]]
     */
    @Query(value = "select user_id as userId, name, age from t_student", nativeQuery = true)
    JSONArray array();

    /**
     * 查询列表
     *
     * @return [{"name":"jackson","userId":1604465665858,"age":100}, {"name":"tomoncle","userId":10,"age":20}]
     */
    @Query(value = "select user_id as userId, name, age from t_student", nativeQuery = true)
    List<JSONObject> list();

    /**
     * 查询列表
     *
     * @return javax.persistence.Tuple
     */
    @Query(value = "select user_id as userId, name, age from t_student", nativeQuery = true)
    List<Tuple> tuples();
}
