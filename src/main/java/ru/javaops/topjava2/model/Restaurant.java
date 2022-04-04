package ru.javaops.topjava2.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Restaurant extends NamedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "address", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String address;

    @Column(name = "telephone")
    @Size(max = 20)
    private String telephone;

//    @CollectionTable(name = "dishes",
//            joinColumns = @JoinColumn(name = "restaurant_id"))
//    @Column(name = "menu")
//    @ElementCollection(fetch = FetchType.EAGER)
//    @JoinColumn(name = "restaurant_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Dish> menu;

    public Restaurant(Integer id, String name, String address, String telephone, Collection<Dish> menu) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
//        setMenu(menu);
    }

    public Restaurant(Integer id, String name, String address, String telephone) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
    }

//    public void setMenu(Collection<Meal> menu) {
//        this.menu = CollectionUtils.isEmpty(menu) ? Set.of() : Set.copyOf(menu);
//    }
}

