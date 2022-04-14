package ru.javaops.topjava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.util.RestaurantUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

//    public List<ResultTo> getRestaurantList() {
//        List<Object[]> results = em.createQuery("SELECT id, name, address, telephone, Quantity FROM Restaurant " +
//                "LEFT JOIN (SELECT RESTAURANT_ID, COUNT(RESTAURANT_ID) AS Quantity FROM Vote GROUP BY RESTAURANT_ID) VOTES_custom " +
//                "ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID").getResultList();
//        for (Object[] result : results){
//            System.out.println(result);
//        }
//        return null;
//    }

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

// select id, name, address, telephone, Quantity from RESTAURANTS LEFT JOIN ( select RESTAURANT_ID , COUNT(RESTAURANT_ID )  AS Quantity FROM VOTES GROUP BY RESTAURANT_ID ) VOTES_custom ON VOTES_custom.RESTAURANT_ID = RESTAURANTS.ID;
}
