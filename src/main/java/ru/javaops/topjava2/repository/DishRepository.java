package ru.javaops.topjava2.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    List<Dish> findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    Optional<Dish> findByIdAndAndRestaurantId(int id, int restaurantId);

    List<Dish> findAllByDate(LocalDate date);
}
