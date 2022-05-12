package ru.javaops.topjava2.web.restaurant;

import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.web.MatcherFactory;

import static ru.javaops.topjava2.web.dish.AdminDishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_MENU =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "vote", "menu.restaurant");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int WRONG_RESTAURANT_ID = 100;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Metropol", "Volgograd", "123-456", menu1);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Пельменная", "Москва", "+7-999-888-77-66", null);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Big fish", "пр.Ленина, 1", "(555) 555-55-55", menu3);

    public static final RestaurantTo restaurantTo1 = new RestaurantTo(RESTAURANT1_ID, "Metropol", "Volgograd", "123-456");
    public static final RestaurantTo restaurantTo2 = new RestaurantTo(RESTAURANT2_ID, "Пельменная", "Москва", "+7-999-888-77-66");
    public static final RestaurantTo restaurantTo3 = new RestaurantTo(RESTAURANT3_ID, "Big fish", "пр.Ленина, 1", "(555) 555-55-55");

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "Somewhere", "555-55-55");
    }

    public static Restaurant getNewWithWrongData() {
        return new Restaurant(null, "New", "", "555-55-55");
    }

    public static Restaurant getNotUniqueName() {
        return new Restaurant(null, "Metropol", "Москва12", "(555) 555-55-77");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "newAddress", "new phone");
    }
}
