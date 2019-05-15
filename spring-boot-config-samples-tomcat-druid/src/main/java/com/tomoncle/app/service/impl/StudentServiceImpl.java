package com.tomoncle.app.service.impl;

import com.tomoncle.app.entity.Student;
import com.tomoncle.app.service.StudentService;
import com.tomoncle.config.springboot.jpa.service.impl.JpaCommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class StudentServiceImpl extends JpaCommonServiceImpl<Student, Integer> implements StudentService {
}
