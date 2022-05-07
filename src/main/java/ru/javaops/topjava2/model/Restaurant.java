package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;
import ru.javaops.topjava2.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(name = "uniq_name", columnNames = "name")
})
//@NamedEntityGraph(name = "Restaurant.menu", attributeNodes = @NamedAttributeNode("menu"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends NamedEntity {

    @Column(name = "address", nullable = false)
    @NotBlank
    @NoHtml
    @Size(max = 255)
    private String address;

    @Column(name = "telephone")
    @NoHtml
    @Size(max = 20)
    private String telephone;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Dish.class)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    @ToString.Exclude
    private Set<Dish> menu;

    public Restaurant(Integer id, String name, String address, String telephone, Collection<Dish> menu) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
        setMenu(menu);
    }

    public Restaurant(Integer id, String name, String address, String telephone) {
        this(id, name, address, telephone, null);
    }

    public void setMenu(Collection<Dish> menu) {
        this.menu = CollectionUtils.isEmpty(menu) ? Set.of() : Set.copyOf(menu);
    }
}

