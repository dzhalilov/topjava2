package ru.javaops.topjava2.web.DishController;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.web.MatcherFactory;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISHTO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH1_ID = 1;
    public static final int DISH2_ID = 2;
    public static final int DISH3_ID = 3;
    public static final int DISH4_ID = 4;
    public static final int DISH5_ID = 5;

    public static final int DISH11_ID = 11;


    public static final Dish dish1 = new Dish(DISH1_ID, "Суп1", 500);
    public static final Dish dish2 = new Dish(DISH2_ID, "Компот", 30);
    public static final Dish dish3 = new Dish(DISH3_ID, "Гречка", 60);
    public static final Dish dish4 = new Dish(DISH4_ID, "Котлета", 150);

    public static final Dish dish5 = new Dish(DISH5_ID, "Пельмени классические", 200);

    public static DishTo getNew() {
        return new DishTo(null, "New", 999);
    }
    public static DishTo getNewWithWrongData() {
        return new DishTo(null, "N", 999);
    }

    public static DishTo getNewAfterSaveInRepo() {
        return new DishTo(DISH11_ID, "New", 999);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "New", 999, restaurant1);
    }

}
