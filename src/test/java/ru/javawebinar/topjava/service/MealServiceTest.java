package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.MealTestData.MEALS_FOR_USER;
import static ru.javawebinar.topjava.MealTestData.MEALS_FOR_ADMIN;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ArtemAA on 10.04.2017.
 */
@ContextConfiguration("classpath:spring/spring-test-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Before
    public void setUp() {
        MEALS_FOR_USER.forEach(meal -> service.save(meal, USER_ID));
        MEALS_FOR_ADMIN.forEach(meal -> service.save(meal, ADMIN_ID));
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(START_SEQ + 2, USER_ID);
        MATCHER.assertEquals(meal, MEALS_FOR_USER.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        Meal meal = service.get(START_SEQ + 2, ADMIN_ID);
        MATCHER.assertEquals(meal, MEALS_FOR_USER.get(0));
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void getBetweenDates() throws Exception {
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(MEALS_FOR_USER, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Перекус", 777);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> expectedList = new ArrayList<>(MEALS_FOR_USER);
        expectedList.add(0, newMeal);
        MATCHER.assertCollectionEquals(expectedList, service.getAll(USER_ID));
    }

}