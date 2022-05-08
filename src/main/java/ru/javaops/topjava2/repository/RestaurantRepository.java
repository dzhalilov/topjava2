package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

//    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
//    @EntityGraph(value = "Restaurant.menu")
//    @Query("SELECT r FROM Restaurant r, Dish d WHERE d.date = current_date")
//    List<Restaurant> getAllWithMenuForToday();
}
