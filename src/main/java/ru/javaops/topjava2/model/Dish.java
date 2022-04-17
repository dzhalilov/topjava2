package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Dish extends NamedEntity {

    @Column(name = "price")
    @PositiveOrZero
    private int price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, int price, @NonNull Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }
}
