package com.tomoncle.app.service;

import com.tomoncle.app.entity.User;
import com.tomoncle.config.springboot.jpa.service.JpaCommonService;

public interface UserService extends JpaCommonService<User, Integer> {
}
