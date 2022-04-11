package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Entity
@Table(name = "dishes")
@Data
//@Getter
//@Setter
@NoArgsConstructor
//@ToString(callSuper = true)
public class Dish extends NamedEntity {

    @Column(name = "price")
    @PositiveOrZero
    private int price;

//    @Column(name = "restaurant_id", nullable = false)
//    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    @JsonBackReference
//    @JsonManagedReference
    private Restaurant restaurant;

    public Dish(int price, Restaurant restaurant) {
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, int price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }
}
