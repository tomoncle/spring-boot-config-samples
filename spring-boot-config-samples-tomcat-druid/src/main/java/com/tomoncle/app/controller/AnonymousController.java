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

import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/test")
@SuppressWarnings("all")
public class AnonymousController {

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/object")
    public List Objects(@RequestParam(value = "id", defaultValue = "-1") int id) {
        String sql = id > 0 ? "select * from t_user where id = " + id : "select * from t_user";
        List list = entityManager.createNativeQuery(sql)
                .unwrap(NativeQueryImpl.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .getResultList();
        entityManager.clear();
        return list;
    }


    @GetMapping("/filed")
    public List filed(@RequestParam(value = "id", defaultValue = "-1") int id) {
        String sql = id > 0 ? "select username from t_user where id = " + id : "select username from t_user";
        List list = entityManager.createNativeQuery(sql)
                .unwrap(Query.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .getResultList();
        entityManager.clear();
        return list;
    }


    @PostMapping
    @Modifying
    @Transactional
    public int save() {
        String sql = "insert into t_user values (3, '平安银行', '111', 234, 'password x', 'sample name')";
        int i = entityManager.createNativeQuery(sql).executeUpdate();
        entityManager.clear();
        return i;
    }


}
