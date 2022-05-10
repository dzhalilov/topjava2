package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.util.RestaurantUtil;
import ru.javaops.topjava2.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = VoteController.REST_URL)
@Slf4j
public class VoteController {
    public static final LocalTime TIME_BEFORE_CAN_REVOTE = LocalTime.of(11, 0);

    static final String REST_URL = "/api/restaurants";
    static final String REST_VOTES = "/votes";
    static final String REST_VOTE = "/vote";
    static final String REST_RESULTS = "/results";

    static final String VOTE_NOT_FOUND = "Vote not found";

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostMapping("/{restaurantId}" + REST_VOTES)
    @Transactional
    public ResponseEntity<String> vote(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("vote for restaurant id={} by user={}", restaurantId, authUser);
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>("No restaurant available!", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime dateTime = LocalDateTime.now();
        Optional<Vote> optionalVote = voteRepository.findByUserIdAndDate(authUser.getUser().id(), dateTime.toLocalDate());
        if (optionalVote.isEmpty()) {
            Vote vote = new Vote(null, dateTime.toLocalDate(), restaurant.get(), authUser.getUser());
            voteRepository.save(vote);
            return new ResponseEntity<>("Your vote was counted.", HttpStatus.CREATED);
        }
        if (dateTime.toLocalTime().isAfter(TIME_BEFORE_CAN_REVOTE)) {
            return new ResponseEntity<>("Too late, vote can't be changed!", HttpStatus.PRECONDITION_FAILED);
        } else {
            optionalVote.get().setRestaurant(restaurant.get());
            return new ResponseEntity<>("Your vote was changed.", HttpStatus.OK);
        }
    }

    @GetMapping("/{restaurantId}" + REST_VOTES)
    public Integer getVotesForRestaurantId(@PathVariable int restaurantId, @RequestParam @Nullable LocalDate date) {
        log.info("get votes for restaurant id={} for date={}", restaurantId, date);
        date = Objects.requireNonNullElseGet(date, LocalDate::now);
        return voteRepository.getCountVotesByRestaurantIdAndDate(restaurantId, date).orElse(0);
    }

    @GetMapping(value = REST_RESULTS, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ResultTo> getVotes(@RequestParam @Nullable LocalDate date) {
        log.info("get all votes for date={}", date);
        date = Objects.requireNonNullElseGet(date, LocalDate::now);
        return voteService.findAllByDateWithVotes(date);
    }

    @GetMapping(value = REST_VOTE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo getRestaurantThatUserVotedFor(@RequestParam @Nullable LocalDate date,
                                                      @AuthenticationPrincipal AuthUser authUser) {
        log.info("get restaurant for date={} for user={}", date, authUser);
        date = Objects.requireNonNullElseGet(date, LocalDate::now);
        Optional<Restaurant> restaurantOptional = restaurantRepository.getByDateAndUserId(date, authUser.getUser().getId());
        return restaurantOptional.map(RestaurantUtil::convertFromRestaurant)
                .orElseThrow(() -> new IllegalRequestDataException(VOTE_NOT_FOUND));
    }
}
