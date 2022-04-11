package ru.javaops.topjava2.web.DishController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = DishController.REST_URL)
@Slf4j
public class DishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurant_id}/dishes";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishService dishService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllByRestaurantId(@PathVariable int restaurant_id) {
        log.info("get all dishes for restaurant id={}", restaurant_id);
        return dishRepository.findAllByRestaurantId(restaurant_id);
    }

//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Dish get(@PathVariable int restaurant_id, @PathVariable int id) {
//        log.info("get dish with id={} for restaurant id={}", id, restaurant_id);
//        Dish dish = dishRepository.findById(id).orElse(null);
//        return (dish != null && dish.getRestaurantId() == restaurant_id) ? dish : null;
//    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public Dish get(@PathVariable int restaurant_id, @PathVariable int id) {
        log.info("get dish with id={} for restaurant id={}", id, restaurant_id);
        Dish dish = dishRepository.findById(id).orElse(null);
        return (dish != null && dish.getRestaurant().id() == restaurant_id) ? dish : null;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Dish> create(@Valid @RequestBody Dish dish, @PathVariable int restaurant_id) {
        log.info("create {} for restaurant id={}", dish, restaurant_id);
        Restaurant restaurant = restaurantRepository.findById(restaurant_id).orElse(null);
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        dish.setRestaurant(restaurant);
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public Dish update(@Valid @RequestBody Dish dish, @PathVariable int restaurant_id, @PathVariable int id) {
        log.info("update {} with id={} for restaurant id={}", dish, id, restaurant_id);
        Restaurant restaurant = restaurantRepository.getById(restaurant_id);
        assureIdConsistent(dish, id);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
        return dish;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int restaurant_id, @PathVariable int id) {
        log.info("delete dish id={} for restaurant id={}", id, restaurant_id);
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null && dish.getRestaurant().id() == restaurant_id) {
            dishRepository.delete(id);
        }
    }
}
