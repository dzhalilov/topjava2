package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("select r from Restaurant r order by r.name")
    List<Restaurant> findAllAndOrderByName();

    @Query(value = "SELECT id, name, address, telephone, Quantity FROM restaurant " +
            "LEFT JOIN (SELECT restaurant_id, COUNT(restaurant_id) AS Quantity FROM vote " +
            "WHERE vote_date=?1 GROUP BY restaurant_id) VOTES_custom ON VOTES_custom.restaurant_id = restaurant.ID " +
            "ORDER BY Quantity DESC, name", nativeQuery = true)
    List<Object[]> findAllByDateWithVotes(LocalDate date);

    @Query("select r from Restaurant r left join r.vote v where v.date =?1 and v.user.id =?2")
    Optional<Restaurant> getByDateAndUserId(LocalDate date, Integer id);

    @Query("select r from Restaurant r join fetch r.menu m where r.id =?1 and m.date =?2 order by r.name, r.address")
    Optional<Restaurant> findByIdWithMenu(int id, LocalDate date);


// select id, name, address, telephone, Quantity from RESTAURANT LEFT JOIN ( select RESTAURANT_ID , COUNT(RESTAURANT_ID )  AS Quantity FROM VOTE where vote_date='2022-05-09' GROUP BY RESTAURANT_ID ) VOTES_custom ON VOTES_custom.RESTAURANT_ID = RESTAURANT.ID

//    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
//    @EntityGraph(value = "Restaurant.menu")
//    @Query("SELECT r FROM Restaurant r, Dish d WHERE d.date = current_date")
//    List<Restaurant> getAllWithMenuForToday();
}
