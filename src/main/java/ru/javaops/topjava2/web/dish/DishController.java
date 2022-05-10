package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.javaops.topjava2.util.DishUtil.*;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.web.restaurant.RestaurantController.RESTAURANT_NOT_FOUND;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "dishes")
public class DishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    static final String DISH_NOT_FOUND = "Dish not found";
    static final String WRONG_RESTAURANT_ID_OR_DISH_ID = "Wrong restaurant id or dish id";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    @Cacheable
    public List<DishTo> getAllByRestaurantId(@PathVariable int restaurantId,
                                             @RequestParam(required = false) LocalDate date) {
        log.info("get all dishes for restaurant id={} for date={}", restaurantId, date);
        date = Objects.requireNonNullElseGet(date, LocalDate::now);
        return dishRepository.findAllByRestaurantIdAndDate(restaurantId, date)
                .stream().map(DishUtil::convertFromDish).toList();
    }

    @GetMapping("/{id}")
    @Cacheable
    public DishTo get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish with id={} for restaurant id={}", id, restaurantId);
        Dish dish = dishRepository.findByIdAndAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException(DISH_NOT_FOUND));
        return convertFromDish(dish);
    }

    @PostMapping
    @Transactional
    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public ResponseEntity<DishTo> create(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        log.info("create {} for restaurant id={}", dishTo, restaurantId);
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalRequestDataException(RESTAURANT_NOT_FOUND);
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurant);
        dishRepository.save(dish);
        DishTo createdTo = convertFromDish(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(dish.getRestaurant().getId(), dish.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant id={}", dishTo, id, restaurantId);
        assureIdConsistent(dishTo, id);
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent() && dish.get().getRestaurant().getId() == restaurantId) {
            updateFromTo(dish.get(), dishTo);
            dishRepository.save(dish.get());
        } else {
            throw new IllegalRequestDataException(WRONG_RESTAURANT_ID_OR_DISH_ID);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "dishes", allEntries = true)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish id={} for restaurant id={}", id, restaurantId);
        dishRepository.deleteByIdAndRestaurantId(id, restaurantId);
    }
}
