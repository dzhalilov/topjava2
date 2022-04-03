package ru.javaops.topjava2.model;


import java.time.LocalDateTime;

public class Vote extends BaseEntity {

    private LocalDateTime dateTime;

    private int restaurant_id;

    private int user_id;
}
