[Project TopJava-2](https://javaops.ru/view/topjava2)
===============================

#### Solution review [graduate project TopJava](https://github.com/JavaOPs/topjava/blob/master/graduation.md)
The task is:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
- - If it is before 11:00 we assume that he changed his mind.
- - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides a new menu each day.
-------------------------------------------------------------
Description API:

There are two roles: Admin and User, which configured for specific paths:
- /api/profile/** - User's profile
- /api/** - for Users (except path: /api/admin/**)
- /api/admin/** - for Admins
 
Paths:
- /api/admin/users - get / put / update / delete Users (for Admin)
- /api/profile - get / delete / register / update User's profile 
- /api/admin/restaurants - getAll / getById / put / update / delete Restaurants (for Admin)
- /api/admin/restaurants/{restaurant_id}/dishes - getAll / getById / put / update / delete Dishes for Restaurant (for Admin)
- /api/profile/votes - vote for Restaurant / get all Restaurants with menu and votes
- /api/restaurants/ - getAll / getById / getWithMenu (for User)
- /api/restaurants/votes - get restaurant that voted for (for User)
- /api/admin/restaurants/{restaurant_id}/votes - vote for restaurant / count votes for restaurant (for User)
- /api/restaurants/result_list - get all restaurants with votes on specific date  
- 
-------------------------------------------------------------
- Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 2.5, Spring Data JPA, Spring Security, Lombok, H2, Caffeine Cache, Swagger/OpenAPI 3.0
- Run: `mvn spring-boot:run` in root directory.
-----------------------------------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  
Credentials:
```
User:  user@yandex.ru / password
Admin: admin@gmail.com / admin
```
