package com.example.reggie.controller;

import com.example.reggie.common.R;
import com.example.reggie.dto.DishDto;
import com.example.reggie.service.DishFlavorService;
import com.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    //新增菜品
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        return null;
    }
}
