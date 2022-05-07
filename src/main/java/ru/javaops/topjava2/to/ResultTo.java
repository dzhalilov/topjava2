package ru.javaops.topjava2.to;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.topjava2.model.Restaurant;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResultTo {

    private Restaurant restaurant;

    private Long votes;

    public ResultTo(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
