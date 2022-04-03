package ru.javaops.topjava2.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

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

//    @CollectionTable(name = "meals",
//            joinColumns = @JoinColumn(name = "restaurant_id"))
//    @Column(name = "menu")
//    @ElementCollection(fetch = FetchType.EAGER)
//    @JoinColumn(name = "restaurant_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Meal> menu;

    public Restaurant(Integer id, String name, String address, String telephone, Collection<Meal> menu) {
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

