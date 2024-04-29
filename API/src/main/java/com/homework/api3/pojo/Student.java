package com.homework.api3.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 钱波
 * @ClassName Student
 * @description: TODO
 * @date 2024年04月28日
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    int id;
    String name;
    String gender;
    String age;
}
