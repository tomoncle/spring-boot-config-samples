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

package com.tomoncle.app.ck.api;


import com.tomoncle.app.ck.dao.StudentRepository;
import com.tomoncle.app.ck.dto.StudentDto;
import com.tomoncle.app.ck.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * @author tomoncle
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        logger.debug("initial StudentController");
        this.studentRepository = studentRepository;
    }

    @GetMapping("/hello")
    public String test() {
        return "hello world";
    }

    @GetMapping("/{id}")
    public Student studentGet(@PathVariable("id") long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.orElse(null);
    }

    @GetMapping
    public List<Student> studentList() {
        return studentRepository.findAll();
    }

    @PostMapping
    public Student studentSave(@RequestBody StudentDto student) {
        logger.debug(student.toString());
        long userId = System.currentTimeMillis();
        int i = studentRepository.save(userId, student.getName(), student.getAge());
        return i > 0 ? studentRepository.findById(userId).orElse(null) : null;
    }

    @DeleteMapping("/{id}")
    public long studentDelete(@PathVariable("id") long userId) {
        studentRepository.delete(userId);
        return userId;
    }

    @PutMapping("/{id}")
    public Student studentUpdate(@PathVariable("id") long userId, @RequestBody StudentDto student) {
        if (!studentRepository.existsById(userId)) {
            return null;
        }
        int i = studentRepository.update(userId, student.getName(), student.getAge());
        return i > 0 ? studentRepository.findById(userId).orElse(null) : null;
    }

}
