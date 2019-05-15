package com.tomoncle.app.service;

import com.tomoncle.app.entity.Major;
import com.tomoncle.app.entity.User;
import com.tomoncle.config.springboot.jpa.service.JpaCommonService;

import java.util.List;

public interface MajorService extends JpaCommonService<Major, Integer> {

    List<Major> andAlwaysTrue();

    int deleteNotWhere();

    int andAlwaysFalse();

    int likeIsTrue();

    int truncate();

    int dropTable();
}
