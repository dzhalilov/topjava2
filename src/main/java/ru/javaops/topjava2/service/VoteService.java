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
import ru.javaops.topjava2.util.VoteUtil;
import ru.javaops.topjava2.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class VoteService {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final DishRepository dishRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public List<ResultTo> getAll() {
        Map<Integer, List<Dish>> restaurantsMenuMap = dishRepository.findAllByDate(LocalDate.now())
                .stream()
                .collect(Collectors.groupingBy(dish -> dish.getRestaurant().getId(), Collectors.toList()));
        Map<Integer, Long> votes = voteRepository.findAllRestaurantsAndCount()
                .stream()
                .collect(Collectors.toMap(v -> (Integer) (v[0]), v -> (Long) v[1]));
        return restaurantRepository.findAll()
                .stream()
                .peek(r -> r.setMenu(restaurantsMenuMap.getOrDefault(r.id(), List.of())))
                .map(ResultTo::new)
                .peek(resultTo -> resultTo.setVotes(votes.get(resultTo.getRestaurant().getId())))
                .toList();
    }

    public ResponseEntity<Object> vote(int restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>("No restaurant available!", HttpStatus.BAD_REQUEST);
        }
        User user = SecurityUtil.authUser();
        LocalDateTime dateTime = LocalDateTime.now();
        Vote vote = voteRepository.findByUserIdAndDate(user.id(), dateTime.toLocalDate());
        if (vote == null) {
            vote = new Vote(null, dateTime.toLocalDate(), restaurant.get(), user);
            voteRepository.save(vote);
            return new ResponseEntity<>("Your vote was counted.", HttpStatus.OK);
        }
        if (dateTime.toLocalTime().isAfter(VoteUtil.TIME_BEFORE_CAN_REVOTE)) {
            return new ResponseEntity<>("Too late, vote can't be changed!", HttpStatus.NOT_ACCEPTABLE);
        } else {
            vote.setRestaurant(restaurant.get());
            return new ResponseEntity<>("Your vote was changed.", HttpStatus.OK);
        }
    }

// select id, name, address, telephone, Quantity from RESTAURANTS LEFT JOIN ( select RESTAURANT_ID , COUNT(RESTAURANT_ID )  AS Quantity FROM VOTES GROUP BY RESTAURANT_ID ) VOTES_custom ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID;
}
