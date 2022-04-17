package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.util.RestaurantUtil;
import ru.javaops.topjava2.util.VoteUtil;
import ru.javaops.topjava2.web.SecurityUtil;

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

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<ResultTo> getAll() {
        Map<Integer, Long> votes = voteRepository.findAllRestaurantsAndCount()
                .stream()
                .collect(Collectors.toMap(v -> (Integer) (v[0]), v -> (Long) v[1]));
        return restaurantRepository.findAll()
                .stream()
                .map(RestaurantUtil::convertFromRestaurant)
                .map(ResultTo::new)
                .peek(r -> r.setVotes(votes.get(r.getRestaurantTo().getId())))
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
