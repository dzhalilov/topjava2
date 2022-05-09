package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.util.ResultUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VoteService {
    public static final LocalTime TIME_BEFORE_CAN_REVOTE = LocalTime.of(11, 0);

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final DishRepository dishRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public List<ResultTo> getAll(LocalDate date) {
        Map<Integer, List<Dish>> restaurantsMenuMap = dishRepository.findAllByDate(date)
                .stream()
                .collect(Collectors.groupingBy(dish -> dish.getRestaurant().getId(), Collectors.toList()));
        Map<Integer, Long> votes = voteRepository.findAllRestaurantsAndCountByDateOrderByQuantityDescAndRestaurantId(date)
                .stream()
                .collect(Collectors.toMap(v -> (Integer) (v[0]), v -> (Long) v[1]));
        return restaurantRepository.findAllAndOrderByName()
                .stream()
                .map(r -> {
                    r.setMenu(restaurantsMenuMap.getOrDefault(r.id(), List.of()));
                    return ResultUtil.convertFromRestaurantAndVote(r, votes.get(r.getId()));
                })
                .toList();
    }

    @Transactional
    public ResponseEntity<String> vote(int restaurantId, User user) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>("No restaurant available!", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime dateTime = LocalDateTime.now();
        Vote vote = voteRepository.findByUserIdAndDate(user.id(), dateTime.toLocalDate());
        if (vote == null) {
            vote = new Vote(null, dateTime.toLocalDate(), restaurant.get(), user);
            voteRepository.save(vote);
            return new ResponseEntity<>("Your vote was counted.", HttpStatus.CREATED);
        }
        if (dateTime.toLocalTime().isAfter(TIME_BEFORE_CAN_REVOTE)) {
            return new ResponseEntity<>("Too late, vote can't be changed!", HttpStatus.PRECONDITION_FAILED);
        } else {
            vote.setRestaurant(restaurant.get());
            return new ResponseEntity<>("Your vote was changed.", HttpStatus.OK);
        }
    }

    public List<ResultTo> findAllByDateWithVotes(LocalDate date) {
        Map<Integer, Long> restaurantsVoteMap = voteRepository.findAllByDate(date)
                .stream()
                .collect(Collectors.groupingBy(v -> v.getRestaurant().getId(), Collectors.counting()));
        return restaurantRepository.findAll()
                .stream()
                .map(r -> ResultUtil.convertFromRestaurantAndVote(r, restaurantsVoteMap.getOrDefault(r.id(), 0L)))
                .sorted((rt1, rt2) -> (rt2.getVotes()).compareTo(rt1.getVotes()))
                .toList();
    }

//    public List<ResultTo> findAllByDateWithVotes(LocalDate date) {
//        return restaurantRepository.findAllByDateWithVotes(date)
//                .stream()
//                .peek(System.out::println)
//                .map(o -> new ResultTo((Restaurant) o[0], (Long) o[1]))
//                .toList();
//    }
// select id, name, address, telephone, Quantity from RESTAURANTS LEFT JOIN ( select RESTAURANT_ID , COUNT(RESTAURANT_ID )  AS Quantity FROM VOTES GROUP BY RESTAURANT_ID ) VOTES_custom ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID;
}
