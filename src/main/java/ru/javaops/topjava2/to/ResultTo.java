package ru.javaops.topjava2.to;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javaops.topjava2.model.Restaurant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultTo {

    private Restaurant restaurant;

    private int votes;

    public ResultTo (Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "ResultTo{ " +
                "restaurant=" + (restaurant != null ? restaurant.getId() : null) +
                ", votes=" + votes +
                '}';
    }
}
