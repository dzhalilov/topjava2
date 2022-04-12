package ru.javaops.topjava2.to;


import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.PositiveOrZero;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class DishTo extends NamedTo {

    @Column(name = "price")
    @PositiveOrZero
    private int price;

    public DishTo(int price) {
        this.price = price;
    }

    public DishTo(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
