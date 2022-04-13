package ru.javaops.topjava2.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.JsonUtil;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.util.DishUtil.convertFromDish;
import static ru.javaops.topjava2.web.dish.DishTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.web.user.UserTestData.ADMIN_MAIL;

class DishControllerTest extends AbstractControllerTest {

    private final String REST_URL = "/api/admin/restaurants/";
    private final String DISHES = "/dishes/";

    @Autowired
    private DishRepository dishRepository;

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
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + DISHES + DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithWrongRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + DISHES + DISH5_ID))
                .andExpect(content().string(""));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo newDishTo = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andExpect(status().isCreated());

        DishTo createdTo = DISHTO_MATCHER.readFromJson(action);
        int newId = createdTo.id();
        newDishTo.setId(newId);
        DISHTO_MATCHER.assertMatch(createdTo, newDishTo);
        DISHTO_MATCHER.assertMatch(convertFromDish(dishRepository.getById(newId)), getNewAfterSaveInRepo());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithWrongDataForDishTo() throws Exception {
        DishTo newDishTo = DishTestData.getNewWithWrongData();
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updatedDishTo = DishTestData.getNew();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + DISHES + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDishTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        updatedDishTo.setId(DISH1_ID);
        DISHTO_MATCHER.assertMatch(convertFromDish(dishRepository.getById(DISH1_ID)), updatedDishTo);
        DISHTO_MATCHER.assertMatch(
                convertFromDish(dishRepository.getById(DISH1_ID)), convertFromDish(DishTestData.getUpdated()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWithWrongDataForDishTo() throws Exception {
        DishTo updatedDishTo = DishTestData.getNewWithWrongData();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + DISHES + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDishTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        DISHTO_MATCHER.assertMatch(convertFromDish(dishRepository.getById(DISH1_ID)), convertFromDish(dish1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID + DISHES + DISH1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(DISH1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteWithWrongId() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID + DISHES + DISH5_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertTrue(dishRepository.findById(DISH5_ID).isPresent());
    }
}