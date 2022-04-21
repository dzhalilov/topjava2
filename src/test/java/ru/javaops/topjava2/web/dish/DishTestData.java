package ru.javaops.topjava2.web.dish;

import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.util.Set;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.restaurant1;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH1_ID = 11;
    public static final int DISH2_ID = 12;
    public static final int DISH3_ID = 13;
    public static final int DISH4_ID = 14;
    public static final int DISH5_ID = 15;
    public static final int DISH6_ID = 16;
    public static final int DISH7_ID = 17;
    public static final int DISH8_ID = 18;
    public static final int DISH9_ID = 19;
    public static final int DISH10_ID = 20;

    public static final int DISH11_ID = 21;
    public static final LocalDate TODAY = LocalDate.now();


    public static final Dish dish1 = new Dish(DISH1_ID, "Суп1", TODAY, 500);
    public static final Dish dish2 = new Dish(DISH2_ID, "Компот", TODAY, 30);
    public static final Dish dish3 = new Dish(DISH3_ID, "Гречка", TODAY, 60);
    public static final Dish dish4 = new Dish(DISH4_ID, "Котлета", TODAY, 150);

    public static final Dish dish5 = new Dish(DISH5_ID, "Пельмени классические", TODAY, 200);
    public static final Dish dish6 = new Dish(DISH6_ID, "Пельмени для детей", TODAY, 100);
    public static final Dish dish7 = new Dish(DISH7_ID, "Пельмени ассорти", TODAY, 300);

    public static final Dish dish8 = new Dish(DISH8_ID, "Уха осетровая", TODAY, 1800);
    public static final Dish dish9 = new Dish(DISH9_ID, "Пельмени классические", TODAY, 1000);
    public static final Dish dish10 = new Dish(DISH10_ID, "Пельмени классические", TODAY, 750);

    public static final Set<Dish> menu1 = Set.of(dish1, dish2, dish3, dish4);
    public static final Set<Dish> menu2 = Set.of(dish5, dish6, dish7);
    public static final Set<Dish> menu3 = Set.of(dish8, dish9, dish10);

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
