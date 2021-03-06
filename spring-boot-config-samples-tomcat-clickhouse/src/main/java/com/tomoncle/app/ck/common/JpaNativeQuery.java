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

package com.tomoncle.app.ck.common;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * @author tomoncle
 */
@Repository
@Transactional
@SuppressWarnings("all")
public class JpaNativeQuery {
    private static final Logger logger = LoggerFactory.getLogger(JpaNativeQuery.class);

    @PersistenceContext
    private EntityManager entityManager;

    public List<Map<String, Object>> queryForMaps(String sql) {
        logger.debug("Execute sql: " + sql);
        List<Map<String, Object>> list = entityManager.createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .getResultList();
        entityManager.clear();
        return list;
    }

    public List<JSONObject> queryForJSONObjects(String sql) {
        logger.debug("Execute sql: " + sql);
        List list = entityManager.createNativeQuery(sql)
                .unwrap(NativeQuery.class)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .getResultList();
        entityManager.clear();
        return list;
    }
}

