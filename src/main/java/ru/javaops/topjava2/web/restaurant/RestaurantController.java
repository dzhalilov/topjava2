package ru.javaops.topjava2.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.util.RestaurantUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurants";
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    @Cacheable
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException(RESTAURANT_NOT_FOUND));
        return RestaurantUtil.convertFromRestaurant(restaurant);
    }

    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @GetMapping
    @Cacheable
    public List<RestaurantTo> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "address"));
        return restaurants.stream().map(RestaurantUtil::convertFromRestaurant).toList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public ResponseEntity<RestaurantTo> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        Restaurant created = restaurantRepository.save(RestaurantUtil.createNewFromTo(restaurantTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(RestaurantUtil.convertFromRestaurant(created));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(cacheNames = "restaurants", allEntries = true)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurantRepository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo));
    }
}
