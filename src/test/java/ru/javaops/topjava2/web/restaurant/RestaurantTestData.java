package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Metropol", "Volgograd", "123-456");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Пельменная", "Москва", "+7-999-888-77-66");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Big fish", "пр.Ленина, 1", "(555) 555-55-55");

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "Somewhere", "555-55-55");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "newAddress", "new phone");
    }
}