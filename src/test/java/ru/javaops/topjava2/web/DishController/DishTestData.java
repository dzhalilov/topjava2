package ru.javaops.topjava2.web.DishController;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.web.MatcherFactory;
import ru.javaops.topjava2.web.restaurant.RestaurantTestData;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISHTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;

    public static final Dish dish1 = new Dish(DISH1_ID, "Суп1", 500);
    public static final Dish dish2 = new Dish(DISH2_ID, "Компот", 30);
    public static final Dish dish3 = new Dish(DISH3_ID, "Гречка", 60);
    public static final Dish dish4 = new Dish(DISH4_ID, "Котлета", 150);

    public static Dish getNew() {
        return new Dish(null, "New", 999);
    }
    public static Dish getUpdated() {
        return new Dish(1, "New", 999, restaurant1);
    }

}
