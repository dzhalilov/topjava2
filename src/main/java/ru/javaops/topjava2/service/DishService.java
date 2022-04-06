package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;

import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishRepository dishRepository;


}
