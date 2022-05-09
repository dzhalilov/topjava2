package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.ResultTo;

@UtilityClass
public class ResultUtil {
    public static ResultTo convertFromRestaurantAndVote(Restaurant restaurant, Long votes) {
        return new ResultTo(restaurant.id(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getTelephone(), votes);
    }
}
