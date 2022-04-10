package ru.javaops.topjava2.to;

import lombok.*;
import ru.javaops.topjava2.HasId;
import ru.javaops.topjava2.model.Restaurant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

//@Value
//@EqualsAndHashCode
//@ToString
public class RestaurantTo extends NamedTo {

    @NotBlank
    @Size(max = 100)
    private String address;

    @Size(max = 20)
    private String telephone;

    public RestaurantTo(Integer id, String name, String address, String telephone) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
    }

    public RestaurantTo() {
    }

//    public static RestaurantTo from(Restaurant restaurant) {
//        RestaurantTo restaurantTo = new RestaurantTo(restaurant.getId(), restaurant.getName());
//        restaurantTo.setAddress(restaurant.getAddress());
//        restaurantTo.setTelephone(restaurant.getTelephone());
//        return restaurantTo;
//    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}