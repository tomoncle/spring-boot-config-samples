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

import com.tomoncle.app.entity.User;
import com.tomoncle.app.service.UserService;
import com.tomoncle.config.springboot.jpa.service.impl.JpaCommonServiceImpl;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;


@Service
@Transactional
public class UserServiceImpl extends JpaCommonServiceImpl<User, Integer> implements UserService {

    private final EntityManager entityManager;

    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Page<User> users(User user, int page, int rows) {
        StringBuilder sb = new StringBuilder("SELECT a.* FROM t_user AS a WHERE 1=1 ");
        if (null != user.getCard() && null != user.getCard().getBackName()) {
            sb.append("AND a.back_name = :name ");
        }
        String querySql = sb.toString();
        String replacePrefix = querySql.substring(0, querySql.indexOf("FROM"));
        String countSql = querySql.replace(replacePrefix, "SELECT COUNT(1) ");

        Query query = entityManager.createNativeQuery(querySql, User.class);
        Query countQuery = entityManager.createNativeQuery(countSql);
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);

        if (null != user.getCard() && null != user.getCard().getBackName()) {
            query.setParameter("name", user.getCard().getBackName());
            countQuery.setParameter("name", user.getCard().getBackName());
        }
        long total = ((BigInteger) countQuery.getSingleResult()).longValue();
        Pageable pageable = PageRequest.of((page - 1), rows);
        List<User> resultList = (List<User>) query.getResultList();
        return new PageImpl<>(resultList, pageable, total);
    }

    @Override
    public User saveUnique(User user) {
        User param = new User();
        param.setUsername(user.getUsername());
        List<User> users = this.findAll(Example.of(param));
        if (users.size() > 0) {
            return users.get(0);
        } else {
            user.setId(new Random().nextInt(10000));
            return this.save(user);
        }
    }
}
