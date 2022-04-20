package ru.javaops.topjava2.web.dish;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH1_ID = 11;
    public static final int DISH2_ID = 12;
    public static final int DISH3_ID = 13;
    public static final int DISH4_ID = 14;
    public static final int DISH5_ID = 15;

    public static final int DISH11_ID = 16;
    public static final LocalDate TODAY = LocalDate.now();


    public static final Dish dish1 = new Dish(DISH1_ID, "Суп1", TODAY, 500);
    public static final Dish dish2 = new Dish(DISH2_ID, "Компот", TODAY, 30);
    public static final Dish dish3 = new Dish(DISH3_ID, "Гречка", TODAY, 60);
    public static final Dish dish4 = new Dish(DISH4_ID, "Котлета", TODAY, 150);

    public static final Dish dish5 = new Dish(DISH5_ID, "Пельмени классические", TODAY, 200);

    public static DishTo getNew() {
        return new DishTo(null, "New", TODAY, 999);
    }

    public static DishTo getNewWithWrongData() {
        return new DishTo(null, "N", TODAY, 999);
    }

    public static DishTo getNewAfterSaveInRepo() {
        return new DishTo(DISH11_ID, "New", TODAY, 999);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "New", TODAY, 999, restaurant1);
    }

}
