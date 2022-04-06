package ru.javaops.topjava2.web.DishController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.service.DishService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = DishController.REST_URL)
@Slf4j
public class DishController {
    static final String REST_URL = "api/restaurants";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishService dishService;

    @GetMapping("/{restaurant_id}/dishes")
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllByRestaurantId(@PathVariable int restaurant_id) {
        return dishRepository.findAllByRestaurantId(restaurant_id);
    }

    @GetMapping("/{restaurant_id}/dishes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Dish> get(@PathVariable int restaurant_id, @PathVariable int id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent() && dish.get().getRestaurantId() == restaurant_id) {
            return dish;
        } else {
            return Optional.empty();
        }
    }
}
