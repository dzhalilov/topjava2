package ru.javaops.topjava2.model;

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
public class Dish extends NamedEntity implements Serializable {

    @Column(name = "price")
    @PositiveOrZero
    private int price;

//    @Column(name = "restaurant_id", nullable = false)
    @NonNull
    @ManyToOne
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;
}
