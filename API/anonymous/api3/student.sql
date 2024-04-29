create table if not exists student
(
    id     int auto_increment comment '学生id'
        primary key,
    name   varchar(100) null comment '姓名',
    gender varchar(8)   null comment '性别',
    age    int          null comment '年龄'
);

