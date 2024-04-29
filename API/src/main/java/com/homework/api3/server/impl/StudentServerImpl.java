package com.homework.api3.server.impl;

import com.homework.api3.mapper.StudentMapper;
import com.homework.api3.pojo.Student;
import com.homework.api3.server.StudentServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 钱波
 * @ClassName StudentServerImpl
 * @description: TODO
 * @date 2024年04月28日
 * @version: 1.0
 */
@Component
public class StudentServerImpl implements StudentServer {
    @Autowired
    StudentMapper studentMapper;
    @Override
    public List<Student> getStudents(int num){
        return studentMapper.getStudents(num);
    }
}
