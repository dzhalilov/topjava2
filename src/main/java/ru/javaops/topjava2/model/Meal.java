package ru.javaops.topjava2.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "meals")
public class Meal extends NamedEntity implements Serializable {
}
