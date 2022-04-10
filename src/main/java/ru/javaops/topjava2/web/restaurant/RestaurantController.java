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
import org.webjars.NotFoundException;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.repository.RestaurantRepository;
import ru.javaops.topjava2.to.RestaurantTo;
import ru.javaops.topjava2.util.RestaurantUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantController.REST_URL)
@Slf4j
@CacheConfig(cacheNames = "restaurants")

// Controller for admin
public class RestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(restaurantRepository.findById(id));
    }

//    @CacheEvict(value = "restaurants", allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @GetMapping
//    @Cacheable
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "address"));
    }

    @PostMapping
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant){
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id){
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurantRepository.save(RestaurantUtil.updateFromTo(restaurant, restaurantTo));
    }
}
