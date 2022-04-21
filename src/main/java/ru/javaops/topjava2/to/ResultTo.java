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

    private Long votes;

    public ResultTo(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
