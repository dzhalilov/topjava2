package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    Vote findByUserIdAndDate(int userId, LocalDate date);

    @Query(value = "select v.restaurant.id, COUNT(v.restaurant.id) AS Quantity FROM Vote AS v GROUP BY v.restaurant.id")
    List<Object[]> findAllRestaurantsAndCount();

}
