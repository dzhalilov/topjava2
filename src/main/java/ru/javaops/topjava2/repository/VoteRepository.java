package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.user.id =?1 and v.date =?2")
    Optional<Vote> findByUserIdAndDate(int userId, LocalDate date);

    @Query("select COUNT (v.restaurant.id) from Vote v where v.restaurant.id =?1 and v.date =?2 group by v.restaurant.id")
    Optional<Integer> getCountVotesByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
