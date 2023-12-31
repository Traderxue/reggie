package com.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie.common.CustomException;
import com.example.reggie.entity.Category;
import com.example.reggie.entity.Dish;
import com.example.reggie.entity.Setmeal;
import com.example.reggie.mapper.CategoryMapper;
import com.example.reggie.service.CategoryService;
import com.example.reggie.service.DishService;
import com.example.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    //根据id删除分类，删除之前需要进行判断
    @Override
    public void remove(Long id) {
        //查询当亲啊分类是否关联菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);

        long count = dishService.count(dishLambdaQueryWrapper);

        if(count >0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类关联了菜品,不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联了，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);

        long count2 = setmealService.count(setmealLambdaQueryWrapper);

        if(count2>0){
            //已经关联了菜单，抛出一个异常
            throw new CustomException("当前分来关联了菜单，不能删除");
        }

        //正常删除分类

        super.removeById(id);
    }
}
