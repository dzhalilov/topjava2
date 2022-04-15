package ru.javaops.topjava2.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
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
}
