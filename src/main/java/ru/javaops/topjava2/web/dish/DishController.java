package ru.javaops.topjava2.web.dish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import java.util.Optional;

import static ru.javaops.topjava2.util.DishUtil.*;
import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.web.restaurant.RestaurantController.RESTAURANT_NOT_FOUND;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class DishController {
    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    static final String DISH_NOT_FOUND = "Dish not found";

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping
    public List<DishTo> getAllByRestaurantId(@PathVariable int restaurantId) {
        log.info("get all dishes for restaurant id={}", restaurantId);
        return dishRepository.findAllByRestaurantIdAndDate(restaurantId, LocalDate.now())
                .stream().map(DishUtil::convertFromDish).toList();
    }

    @GetMapping("/{id}")
    public DishTo get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("get dish with id={} for restaurant id={}", id, restaurantId);
        Dish dish = dishRepository.findByIdAndAndRestaurantId(id, restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException(DISH_NOT_FOUND));
        return convertFromDish(dish);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "votes", allEntries = true)
    @Transactional
    public ResponseEntity<DishTo> create(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId) {
        log.info("create {} for restaurant id={}", dishTo, restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalRequestDataException(RESTAURANT_NOT_FOUND));
//        if (restaurant.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
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
    @CacheEvict(cacheNames = "votes", allEntries = true)
    @Transactional
    public DishTo update(@Valid @RequestBody DishTo dishTo, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("update {} with id={} for restaurant id={}", dishTo, id, restaurantId);
        assureIdConsistent(dishTo, id);
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isEmpty() || dish.get().getRestaurant().id() != restaurantId) {
            return null;
        }
        updateFromTo(dish.get(), dishTo);
        dishRepository.save(dish.get());
        return dishTo;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "votes", allEntries = true)
    @Transactional
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("delete dish id={} for restaurant id={}", id, restaurantId);
        Dish dish = dishRepository.findById(id).orElse(null);
        if (dish != null && dish.getRestaurant().id() == restaurantId) {
            dishRepository.deleteById(id);
        }
    }
}
