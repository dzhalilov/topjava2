package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import ru.javaops.topjava2.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class Dish extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "dish_date")
    @NotNull
    @CreatedDate
    LocalDate date;

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    @NoHtml
    protected String name;

    @Column(name = "price")
    @PositiveOrZero
    private int price;

    public Dish(Integer id, String name, LocalDate date, int price) {
        super(id);
        this.name = name;
        this.date = date;
        this.price = price;
    }

    public Dish(Integer id, String name, LocalDate date, int price, @NonNull Restaurant restaurant) {
        super(id);
        this.name = name;
        this.date = date;
        this.price = price;
        this.restaurant = restaurant;
    }
}
