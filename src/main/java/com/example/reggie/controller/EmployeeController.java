package com.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggie.common.R;
import com.example.reggie.entity.Employee;
import com.example.reggie.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password =  employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Employee::getUsername,employee.getUsername());

        Employee emp= employeeService.getOne(queryWrapper);

        if(emp == null){
            return R.error("登录失败");
        }
        if(!emp.getPassword().equals(password)){
             return  R.error("登录失败");
        }
        if(emp.getStatus()==0){
            return  R.error("账户已禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());

        return  R.success(emp);
    }
}
