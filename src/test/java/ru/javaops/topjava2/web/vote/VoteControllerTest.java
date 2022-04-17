package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.AbstractControllerTest;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.USER_ID;
import static ru.javaops.topjava2.web.user.UserTestData.USER_MAIL;
import static ru.javaops.topjava2.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT2_ID))
                .andExpect(status().isOk());
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, TODAY.toLocalDate());
        Vote expected = getVote();
        expected.setId(actual.getId());
        assertEquals(expected, actual);
        assertEquals(votes.size() + 1, voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteForWrongRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + WRONG_RESTAURANT_ID))
                .andExpect(status().isBadRequest());
        assertEquals(votes, voteRepository.findAll());
    }

    @Test
    void voteByAnonymous() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                .andExpect(status().isUnauthorized());
        assertEquals(votes, voteRepository.findAll());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteBeforeEleven() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_BEFORE_ELEVEN.toLocalDate());
        expected.setRestaurant(restaurant3);
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_BEFORE_ELEVEN);
            perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                    .andExpect(status().isOk());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_BEFORE_ELEVEN.toLocalDate());
        assertEquals(expected, actual);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteAfterEleven() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_AFTER_ELEVEN.toLocalDate());
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_AFTER_ELEVEN);
            perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                    .andExpect(status().isNotAcceptable());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_AFTER_ELEVEN.toLocalDate());
        assertEquals(expected, actual);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteOnBoundary() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_ELEVEN.toLocalDate());
        expected.setRestaurant(restaurant3);
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_ELEVEN);
            perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                    .andExpect(status().isOk());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_ELEVEN.toLocalDate());
        assertEquals(expected, actual);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getResultList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(RESULT_TO_MATCHER.contentJson(resultsWithPopulatedData));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getResultListHundredTimes() throws Exception {
        Long startTesting = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            perform(MockMvcRequestBuilders.get(REST_URL));
        }
        Long endTesting = new Date().getTime();
        Assertions.assertTrue((endTesting - startTesting) < 1000);
    }
}