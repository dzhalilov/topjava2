package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("select d from Dish d where d.restaurant.id = ?1 and d.date = ?2")
    List<Dish> findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @Query("select d from Dish d where d.id = ?1 and d.restaurant.id = ?2")
    Optional<Dish> findByIdAndAndRestaurantId(int id, int restaurantId);

    List<Dish> findAllByDate(LocalDate date);

    @Transactional
    @Modifying
    @Query("delete from Dish d where d.id =?1 and d.restaurant.id = ?2")
    void deleteByIdAndRestaurantId(int id, int restaurantId);

//    @Transactional
//    @Modifying
//    @Query("update Dish d set d.name =: name, d.price =: price, d.date =: dishDate where d.id =: id and d.restaurant.id =: restaurantId")
//    void updateDish(int id, int restaurantId, String name, int price, LocalDate dishDate);
}
