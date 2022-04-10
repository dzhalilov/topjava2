package ru.javaops.topjava2.util;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Role;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.to.UserTo;

public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress(), restaurantTo.getTelephone());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        restaurant.setTelephone(restaurantTo.getTelephone());
        return restaurant;
    }

    public static RestaurantTo convertFromRestaurant(Restaurant restaurant) {
        return new RestaurantTo (restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getTelephone());
    }
}
