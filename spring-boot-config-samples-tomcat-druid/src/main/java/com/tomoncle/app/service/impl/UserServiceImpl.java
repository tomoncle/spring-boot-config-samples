package com.tomoncle.app.service.impl;

import com.tomoncle.app.entity.User;
import com.tomoncle.app.service.UserService;
import com.tomoncle.config.springboot.jpa.service.impl.JpaCommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl extends JpaCommonServiceImpl<User, Integer> implements UserService {
}
