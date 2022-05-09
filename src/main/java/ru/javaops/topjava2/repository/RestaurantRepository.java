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

    // correct count, but show just exist vote  @Query("select r, COUNT(r.id) as Quantity from Restaurant r left join Vote v on r.id = v.restaurant.id where v.date =?1 group by r.id order by Quantity DESC")
    @Query("SELECT r, count(v.id) as Quantity from Restaurant r left join r.vote v where v.date =?1 group by r.id order by Quantity desc ")
//    @Query("select r, COUNT(r) as Quantity from Restaurant r join r.vote group by r order by Quantity DESC")
//    @Query("select r, COUNT (r.id) as Quantity from Restaurant r where (select count(v.restaurant.id) from Vote v where v.date =?1 group by v.restaurant.id) >= 0 order by Quantity DESC")
// count all votes  @Query("select r, count (r.id) as Quantity from Restaurant r left join Vote v on r.id = v.restaurant.id where (select count(v.restaurant.id) from Vote v where v.date =?1 group by v.restaurant.id) >= 0 group by r.id order by Quantity desc ")
// count rest id    @Query("select r, count (r.id) as Quantity from Restaurant r left join Vote v on r.id = v.restaurant.id and v.date =?1 group by r.id order by Quantity desc ")
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
