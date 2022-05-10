package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.ResultTo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<ResultTo> findAllByDateWithVotes(LocalDate date) {
        return restaurantRepository.findAllByDateWithVotes(date)
                .stream()
                .map(o -> new ResultTo((Integer) o[0], (String) o[1], (String) o[2], (String) o[3],
                        o[4] != null ? ((BigInteger) o[4]).longValue() : 0L))
                .toList();
    }
}
