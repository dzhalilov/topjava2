package ru.javaops.topjava2.util;

import lombok.experimental.UtilityClass;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.VoteTo;

import java.time.LocalDate;

@UtilityClass
public class VoteUtil {
    public static VoteTo convertFromRestaurantAndVote(LocalDate date, Restaurant restaurant) {
        return new VoteTo(date, RestaurantUtil.convertFromRestaurant(restaurant));
    }

    public static VoteTo convertFromVote(Vote vote) {
        return new VoteTo(vote.getDate(), RestaurantUtil.convertFromRestaurant(vote.getRestaurant()));
    }
}
