package com.homework.api3.controller;

import com.homework.api3.Other.Result;
import com.homework.api3.pojo.Student;
import com.homework.api3.server.impl.StudentServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author 钱波
 * @ClassName StudentController
 * @description: TODO
 * @date 2024年04月28日
 * @version: 1.0
 */
@RestController
public class StudentController {
    @Autowired
    StudentServerImpl studentServerImpl;

    @GetMapping("/students")
    public Result getStudentById(int num){
        if ( num > 1000 || num < 1){
            return Result.error("查询数目不合法");
        }
        List<Student> students = studentServerImpl.getStudents(num);
        return Result.success(students);
    }
}
