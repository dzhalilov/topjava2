package ru.javaops.topjava2.to;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultTo {

    private RestaurantTo restaurantTo;

    private Long votes;

    public ResultTo(RestaurantTo restaurantTo) {
        this.restaurantTo = restaurantTo;
    }

    @Override
    public String toString() {
        return "ResultTo{ " +
                "restaurant=" + (restaurantTo != null ? restaurantTo.getId() : null) +
                ", votes=" + votes +
                '}';
    }
}