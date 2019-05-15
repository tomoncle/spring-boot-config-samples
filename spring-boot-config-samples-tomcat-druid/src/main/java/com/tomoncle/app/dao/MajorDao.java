package com.tomoncle.app.dao;

import com.tomoncle.app.entity.Major;
import com.tomoncle.config.springboot.jpa.repository.JpaCommonRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorDao extends JpaCommonRepository<Major, Integer> {

    @Query("select id, name, description from t_major where 1 = 1 and 2 = 2 ")
    List<Major> andAlwaysTrue();

    @Query("delete from t_major")
    int deleteNotWhere();

    int dropTable();

    int truncate();

    @Query("select id, name, description from t_major where 1 = 2 and 0 = 2 ")
    int andAlwaysFalse();

    @Query("select id, name, description from t_major where id is not null and name like '%'")
    int likeIsTrue();
}
