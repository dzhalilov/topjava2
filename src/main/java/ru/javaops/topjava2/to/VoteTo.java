package ru.javaops.topjava2.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteTo {

    private LocalDate date;
    private RestaurantTo restaurantTo;
}
