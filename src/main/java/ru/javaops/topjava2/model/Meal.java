package ru.javaops.topjava2.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Entity
@Table(name = "meals")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Meal extends NamedEntity implements Serializable {

    @Column(name = "price")
    @PositiveOrZero
    private int price;

    @Column(name = "restaurant_id", nullable = false)
    @NonNull
    private int restaurant_id;
}
