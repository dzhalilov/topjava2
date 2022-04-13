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
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/{restaurant_id}")
    @Transactional
    public ResponseEntity<Object> vote(@PathVariable int restaurant_id) {
        Vote vote = voteRepository.findByRestaurantId(restaurant_id);
        LocalDateTime dateTime = LocalDateTime.now();
        if (vote == null) {
            return new ResponseEntity<>("No restaurant available!", HttpStatus.BAD_REQUEST);
        } else if (false) {
            return new ResponseEntity<>("You've already voted today!", HttpStatus.NOT_ACCEPTABLE);
        } else {

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


}
