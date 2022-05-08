package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.web.AuthUser;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/profile/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<Object> vote(@PathVariable int restaurant_id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("vote for restaurant id={} by user={}", restaurant_id, authUser);
        return voteService.vote(restaurant_id, authUser.getUser());
    }

    @GetMapping
    public List<ResultTo> getRestaurantsWithVotes() {
        log.info("get all restaurants with votes");
        return voteService.getAll();
    }
}
