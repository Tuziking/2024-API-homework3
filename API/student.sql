use api3;
create table if not exists student
(
    id     int auto_increment comment '学生id'
        primary key,
    name   varchar(100) null comment '姓名',
    gender varchar(8)   null comment '性别',
    age    int          null comment '年龄'
);

CREATE PROCEDURE insert_random_students(IN num_rows INT)
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE name VARCHAR(100);
    DECLARE gender VARCHAR(8);
    DECLARE age INT;

    WHILE i < num_rows DO
            SET name = CONCAT('Student', FLOOR(1 + RAND() * 10000)); -- 随机生成名字
            SET gender = CASE WHEN RAND() > 0.5 THEN 'Male' ELSE 'Female' END; -- 随机生成性别
            SET age = FLOOR(18 + RAND() * 10); -- 随机生成年龄（18到27岁）

INSERT INTO student (name, gender, age) VALUES (name, gender, age);

SET i = i + 1;
END WHILE;
END;

