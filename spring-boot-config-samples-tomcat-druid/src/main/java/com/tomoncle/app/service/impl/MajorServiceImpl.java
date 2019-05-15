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

    @Override
    public int deleteNotWhere() {
        return majorDao.deleteNotWhere();
    }

    @Override
    public int andAlwaysFalse() {
        return majorDao.andAlwaysFalse();
    }

    @Override
    public int likeIsTrue() {
        return majorDao.likeIsTrue();
    }

    @Override
    public int truncate() {
        return majorDao.truncate();
    }

    @Override
    public int dropTable() {
        return majorDao.dropTable();
    }
}
