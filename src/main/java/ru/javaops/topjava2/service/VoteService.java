package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.ResultTo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteService {
    private RestaurantRepository restaurantRepository;
    private VoteRepository voteRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
//        this.em = em;
    }

//    public List<ResultTo> getRestaurantList() {
//        List<Object[]> results = em.createQuery("SELECT id, name, address, telephone, Quantity FROM RESTAURANTS " +
//                "LEFT JOIN (SELECT RESTAURANT_ID, COUNT(RESTAURANT_ID) AS Quantity FROM VOTES GROUP BY RESTAURANT_ID) VOTES_custom " +
//                "ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID").getResultList();
//        for (Object[] result : results){
//            System.out.println(result);
//        }
//        return null;
//    }

    public List<ResultTo> getAll() {
//        List<ResultTo> resultTos = new ArrayList<>();
//        List<Restaurant> restaurants = restaurantRepository.findAll();
//        resultTos = restaurants.stream().map(ResultTo::new).toList();
//                List<Object[]> x = voteRepository.findAllRestaurantsAndCount();
//        Map<Integer, Integer> votes = voteRepository.findAllRestaurantsAndCount()
//                .stream()
//                .collect(Collectors.toMap(v -> Integer.valueOf(v[0]), v -> Integer.valueOf(v[1])));
        return null;
    }

// select id, name, address, telephone, Quantity from RESTAURANTS LEFT JOIN ( select RESTAURANT_ID , COUNT(RESTAURANT_ID )  AS Quantity FROM VOTES GROUP BY RESTAURANT_ID ) VOTES_custom ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID;
}
