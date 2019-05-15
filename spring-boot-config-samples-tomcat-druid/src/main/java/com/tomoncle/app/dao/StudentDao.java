package com.tomoncle.app.dao;

import com.tomoncle.app.entity.Student;
import com.tomoncle.config.springboot.jpa.repository.JpaCommonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaCommonRepository<Student, Integer> {
}
