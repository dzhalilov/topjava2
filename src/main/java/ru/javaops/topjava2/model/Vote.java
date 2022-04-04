package ru.javaops.topjava2.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends BaseEntity {

    private LocalDate date;

    private int restaurant_id;

    private int user_id;
}
