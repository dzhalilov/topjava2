package ru.javaops.topjava2.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

@Transactional(readOnly = true)
@Repository
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
