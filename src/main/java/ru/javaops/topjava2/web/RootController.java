package ru.javaops.topjava2.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.service.VoteService;
import ru.javaops.topjava2.to.ResultTo;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = RootController.REST_URL)
public class RootController {

    static final String REST_URL = "/api";

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteService voteService;

    @GetMapping
    @Transactional
    public List<ResultTo> getRestaurantsWithVotes() {
        return voteService.getAll();

    }
}
