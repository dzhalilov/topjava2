package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.SecurityUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile/votes";
    static final LocalTime TIME_BEFORE_CAN_REVOTE = LocalTime.of(11, 0);

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurant_id}")
    @Transactional
    public ResponseEntity<Object> vote(@PathVariable int restaurant_id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurant_id);
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
        if (dateTime.toLocalTime().isAfter(TIME_BEFORE_CAN_REVOTE)) {
            return new ResponseEntity<>("Too late, vote can't be changed!", HttpStatus.NOT_ACCEPTABLE);
        } else {
            vote.setRestaurant(restaurant.get());
            return new ResponseEntity<>("Your vote was changed.", HttpStatus.OK);
        }
    }


}
