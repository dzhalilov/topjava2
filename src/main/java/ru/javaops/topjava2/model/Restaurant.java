package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(name = "uniqName", columnNames = "name")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"menu"})
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Dish.class)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<Dish> menu;

    public Restaurant(Integer id, String name, String address, String telephone, Collection<Dish> menu) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
        setMenu(menu);
    }

    public Restaurant(Integer id, String name, String address, String telephone) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
    }

    public void setMenu(Collection<Dish> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Set.of() : Set.copyOf(menu);
    }
}

