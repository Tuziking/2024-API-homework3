package com.homework.api3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.homework.api3.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 钱波
 * @ClassName StudentMapper
 * @description: TODO
 * @date 2024年04月28日
 * @version: 1.0
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Select("SELECT * FROM student ORDER BY RAND() LIMIT #{num}")
    List<Student> getStudents(int num);
}
