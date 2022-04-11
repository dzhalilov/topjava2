package ru.javaops.topjava2.web.DishController;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.web.MatcherFactory;
import ru.javaops.topjava2.web.restaurant.RestaurantTestData;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;

    public static final Dish dish1 = new Dish(DISH1_ID, "Суп1", 500, RestaurantTestData.restaurant1);
    public static final Dish dish2 = new Dish(DISH2_ID, "Компот", 30, RestaurantTestData.restaurant1);
    public static final Dish dish3 = new Dish(DISH3_ID, "Гречка", 60, RestaurantTestData.restaurant1);
    public static final Dish dish4 = new Dish(DISH4_ID, "Котлета", 150, RestaurantTestData.restaurant1);

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "Somewhere", "555-55-55");
    }

}
