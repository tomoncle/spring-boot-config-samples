package com.tomoncle.app.service;

import com.tomoncle.app.entity.Student;
import com.tomoncle.config.springboot.jpa.service.JpaCommonService;

public interface StudentService extends JpaCommonService<Student, Integer> {
}