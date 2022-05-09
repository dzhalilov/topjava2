package ru.javaops.topjava2.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.util.RestaurantUtil;

import java.util.List;

public abstract class AbstractRestaurantController {
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found";

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantTo get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException(RESTAURANT_NOT_FOUND));
        return RestaurantUtil.convertFromRestaurant(restaurant);
    }

    public List<RestaurantTo> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "address"));
        return restaurants.stream().map(RestaurantUtil::convertFromRestaurant).toList();
    }
}
