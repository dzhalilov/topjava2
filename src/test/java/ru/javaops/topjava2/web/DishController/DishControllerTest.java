package ru.javaops.topjava2.web.DishController;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javaops.topjava2.web.AbstractControllerTest;
import ru.javaops.topjava2.web.restaurant.RestaurantTestData;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javaops.topjava2.web.DishController.DishTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class DishControllerTest extends AbstractControllerTest {

    private final String REST_URL = "/api/admin/restaurants/";
    private final String DISHES = "/dishes/";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + DISHES))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2, dish3, dish4));

    }

    @Test
    void get() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}