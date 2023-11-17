package com.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

//员工实体类
@Data
public class Employee {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;        //在yml中开启驼峰命名

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)   //插入时填充字段
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)         //插入和更新时填充字段
    private Long updateUser;



}
