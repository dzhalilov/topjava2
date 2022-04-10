package ru.javaops.topjava2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DishRepository extends BaseRepository<Dish>{

    List<Dish> findAllByRestaurantId(int restaurantId);
}
