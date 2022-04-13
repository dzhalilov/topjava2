package ru.javaops.topjava2.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.topjava2.web.restaurant.RestaurantTestData.*;
import static ru.javaops.topjava2.web.user.UserTestData.*;
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
        assertEquals(7, voteRepository.count());
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
//
    // TODO: Fix LocalTimeProblem
    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteBeforeEleven() throws Exception {
//        String instantExpected = "2022-04-04T10:15:30Z";
//        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("Europe/Moscow"));
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk());
        assertEquals(7, voteRepository.findAll().size());
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                .andExpect(status().isOk());
        assertEquals(7, voteRepository.findAll().size());
//        Vote vote = new Vote(vote1.getId(), vote1.getDate(), restaurant3, vote1.getUser());
//        assertEquals(voteRepository.findById(VOTE1_ID).get(), vote);
    }

    // TODO: Fix LocalTimeProblem
    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteAfterEleven() throws Exception {
//        String instantExpected = "2022-04-04T11:15:30Z";
//        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("Europe/Moscow"));
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk());
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT3_ID))
                .andExpect(status().isNotAcceptable());
        assertEquals(7, voteRepository.findAll().size());
    }
}