package ru.javaops.topjava2.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.ResultTo;

import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "votes")
public class VoteController {

    static final String REST_URL = "/api/profile/votes/api";

    @Autowired
    VoteService voteService;

    @GetMapping("/{restaurant_id}")
    @CacheEvict(cacheNames = "votes", allEntries = true)
    public ResponseEntity<Object> vote(@PathVariable int restaurant_id) {
        log.info("vote for restaurant id={}", restaurant_id);
        return voteService.vote(restaurant_id);
    }

    @GetMapping
    @Cacheable("votes")
    public List<ResultTo> getRestaurantsWithVotes() {
        log.info("get all restaurants with votes");
        return voteService.getAll();
    }


}
