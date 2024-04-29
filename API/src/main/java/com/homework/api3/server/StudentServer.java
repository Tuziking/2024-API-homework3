package com.homework.api3.server;

import com.homework.api3.pojo.Student;

import java.util.List;

/**
 * @author 钱波
 * @ClassName StudentServer
 * @description: TODO
 * @date 2024年04月28日
 * @version: 1.0
 */
public interface StudentServer {
    List<Student> getStudents(int num);
}
