package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.DishRepository;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.util.DishUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.DishUtil.*;
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

    @GetMapping()
    public List<DishTo> getAllByRestaurantId(@PathVariable int restaurant_id) {
        log.info("get all dishes for restaurant id={}", restaurant_id);
        return dishRepository.findAllByRestaurantId(restaurant_id)
                .stream().map(DishUtil::convertFromDish).toList();
    }

    @GetMapping("/{id}")
    @Transactional
    public DishTo get(@PathVariable int restaurant_id, @PathVariable int id) {
        log.info("get dish with id={} for restaurant id={}", id, restaurant_id);
        Dish dish = dishRepository.findByIdAndAndRestaurantId(id, restaurant_id);
//        if (dish == null) {
//            throw new IllegalRequestDataException("Wrong restaurant or dish");
//        }
//        return convertFromDish(dish);
        return dish != null ? convertFromDish(dish) : null;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DishTo> create(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurant_id) {
        log.info("create {} for restaurant id={}", dishTo, restaurant_id);
        Restaurant restaurant = restaurantRepository.findById(restaurant_id).orElse(null);
        if (restaurant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
        DishTo createdTo = convertFromDish(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(dish.getRestaurant().getId(), dish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public DishTo update(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurant_id, @PathVariable int id) {
        log.info("update {} with id={} for restaurant id={}", dishTo, id, restaurant_id);
        assureIdConsistent(dishTo, id);
        Dish dish = dishRepository.getById(id);
        if (dish == null || dish.getRestaurant().id() != restaurant_id) {
            return null;
        }
        updateFromTo(dish, dishTo);
        dishRepository.save(dish);
        return dishTo;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int restaurant_id, @PathVariable int id) {
        log.info("delete dish id={} for restaurant id={}", id, restaurant_id);
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null && dish.getRestaurant().id() == restaurant_id) {
            dishRepository.deleteById(id);
        }
    }
}
