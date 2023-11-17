package com.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie.dto.SetmealDto;
import com.example.reggie.entity.SetmealDish;

public interface SetmealDishService extends IService<SetmealDish> {
    public void saveWidthDish(SetmealDto setmealDto);

    void save(SetmealDto setmealDto);
}
