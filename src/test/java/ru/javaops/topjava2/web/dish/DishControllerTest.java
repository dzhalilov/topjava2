package ru.javaops.topjava2.web.dish;

import org.hamcrest.Matchers;
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
import ru.javaops.topjava2.web.GlobalExceptionHandler;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.util.DishUtil.convertFromDish;
import static ru.javaops.topjava2.web.dish.DishTestData.*;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.WRONG_RESTAURANT_ID;
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
                .andExpect(DISH_MATCHER.contentJson(dish3, dish2, dish4, dish1));
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
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalRequestDataException))
                .andExpect(result -> assertEquals(DishController.DISH_NOT_FOUND,
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo newDishTo = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andExpect(status().isCreated());

        DishTo createdTo = DISH_TO_MATCHER.readFromJson(action);
        int newId = createdTo.id();
        newDishTo.setId(newId);
        DishTo savedDishTo = convertFromDish(dishRepository.getById(newId));
        DishTo expected = getNewAfterSaveInRepo();
        expected.setId(savedDishTo.getId());
        DISH_TO_MATCHER.assertMatch(createdTo, newDishTo);
        DISH_TO_MATCHER.assertMatch(savedDishTo, expected);
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
    void createWithWrongRestaurantId() throws Exception {
        DishTo newDishTo = DishTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL + WRONG_RESTAURANT_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithNotUniqueDateAndName() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getWithNotUniqueDateAndName())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(Matchers.containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_DISH_DATE_NAME)));
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
        DISH_TO_MATCHER.assertMatch(convertFromDish(dishRepository.getById(DISH1_ID)), updatedDishTo);
        DISH_TO_MATCHER.assertMatch(
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
        DISH_TO_MATCHER.assertMatch(convertFromDish(dishRepository.getById(DISH1_ID)), convertFromDish(dish1));
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
                .andExpect(status().isUnprocessableEntity());
        assertTrue(dishRepository.findById(DISH5_ID).isPresent());
    }
}