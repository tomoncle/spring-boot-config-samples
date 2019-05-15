package com.tomoncle.app.dao;

import com.tomoncle.app.entity.User;
import com.tomoncle.config.springboot.jpa.repository.JpaCommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaCommonRepository<User, Integer> {
}
