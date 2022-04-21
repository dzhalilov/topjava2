package ru.javaops.topjava2.web.vote;

import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.to.ResultTo;
import ru.javaops.topjava2.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.admin;
import static ru.javaops.topjava2.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<ResultTo> RESULT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ResultTo.class);

    public static final LocalDateTime TODAY = LocalDateTime.now();
    public static final LocalDateTime DATE_TIME_BEFORE_ELEVEN = LocalDateTime.of(2022, 4, 4, 10, 50);
    public static final LocalDateTime DATE_TIME_AFTER_ELEVEN = LocalDateTime.of(2022, 4, 4, 11, 15);
    public static final LocalDateTime DATE_TIME_ELEVEN = LocalDateTime.of(2022, 4, 4, 11, 0);

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;
    public static final int VOTE3_ID = 3;
    public static final int VOTE4_ID = 4;
    public static final int VOTE5_ID = 5;
    public static final int VOTE6_ID = 6;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2022, 4, 4), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE2_ID, LocalDate.of(2022, 4, 4), restaurant3, admin);
    public static final Vote vote3 = new Vote(VOTE3_ID, LocalDate.of(2022, 4, 3), restaurant2, user);
    public static final Vote vote4 = new Vote(VOTE4_ID, LocalDate.of(2022, 4, 3), restaurant2, admin);
    public static final Vote vote5 = new Vote(VOTE5_ID, LocalDate.of(2022, 4, 2), restaurant1, user);
    public static final Vote vote6 = new Vote(VOTE6_ID, LocalDate.of(2022, 4, 2), restaurant1, admin);

    public static final List<Vote> votes = List.of(vote1, vote2, vote3, vote4, vote5, vote6);

    public static Vote getVote() {
        return new Vote(null, LocalDate.now(), restaurant2, user);
    }

    public static List<ResultTo> resultsWithPopulatedData = List.of(
            new ResultTo(restaurant1, 3L),
            new ResultTo(restaurant2, 2L),
            new ResultTo(restaurant3, 1L));
}
