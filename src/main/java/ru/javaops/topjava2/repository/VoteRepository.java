package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.user.id =?1 and v.date =?2")
    Vote findByUserIdAndDate(int userId, LocalDate date);

    @Query("select v.restaurant.id, COUNT(v.restaurant.id) AS Quantity FROM Vote v where v.date = ?1 " +
            "GROUP BY v.restaurant.id order by Quantity DESC, v.restaurant.id asc")
    List<Object[]> findAllRestaurantsAndCountByDateOrderByQuantityDescAndRestaurantId(LocalDate date);

    @Query("select COUNT (v.restaurant.id) from Vote v where v.restaurant.id =?1 and v.date =?2 group by v.restaurant.id")
    Integer getCountVotesByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @Query("select v from Vote v where v.date =?1 order by v.id")
    List<Vote> findAllByDate(LocalDate date);
}
