package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * MrArtemAA
 * 14.05.2017
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null,
                LocalDateTime.of(2017, 5, 14, 15, 0,0),
                "Обед", 1300);

        ResultActions action = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);

        updated.setDescription("Полдник");
        updated.setCalories(200);
        updated.setDateTime(LocalDateTime.of(2017, 5, 14, 16, 30, 0));

        mockMvc.perform(put(REST_URL + MEAL1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testDelete() throws Exception {
        AuthorizedUser.setId(ADMIN_ID);
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL_ID))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), mealService.getAll(ADMIN_ID));

        AuthorizedUser.setId(USER_ID);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_WITH_EXCEED_MATCHER.contentListMatcher(
                        MealsUtil.getWithExceeded(MEALS, AuthorizedUser.getCaloriesPerDay())));
    }

    @Test
    public void testGetBetween() throws Exception {
        final String FILTER = "filter";
        final String DATETIME1 = "2015-05-30T05:00:00";
        final String DATETIME2 = "2015-05-30T23:59:00";

        mockMvc.perform(get(REST_URL + FILTER)
            .param("startDateTime", DATETIME1)
            .param("endDatetime", DATETIME2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_WITH_EXCEED_MATCHER.contentListMatcher(MealsUtil.getWithExceeded(
                        Arrays.asList(MEAL3, MEAL2, MEAL1),
                        AuthorizedUser.getCaloriesPerDay()
                )));
    }

}