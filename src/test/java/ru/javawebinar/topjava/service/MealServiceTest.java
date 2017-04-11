package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ArtemAA on 10.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(START_SEQ + 2, USER_ID);
        MATCHER.assertEquals(meal, MEALS_FOR_USER.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserMeal() throws Exception {
        service.get(START_SEQ + 2, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(START_SEQ + 2, USER_ID);
        List<Meal> expected = new ArrayList<>(MEALS_FOR_USER);
        expected.remove(0);
        MATCHER.assertCollectionEquals(expected, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUserMeal() throws Exception {
        service.delete(START_SEQ + 2, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> list = service.getBetweenDates(DATE1.toLocalDate(), DATE1.toLocalDate(), USER_ID);
        MATCHER.assertCollectionEquals(MEALS_FOR_USER, list);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> list = service.getBetweenDateTimes(DATE1, DATE2, USER_ID);
        List<Meal> expected = new ArrayList<>(MEALS_FOR_USER);
        expected.remove(2);
        MATCHER.assertCollectionEquals(list, expected);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(MEALS_FOR_USER, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal meal = service.get(START_SEQ + 2, USER_ID);
        meal.setDescription("Обжорыш");
        meal.setCalories(2000);
        service.update(meal, USER_ID);
        MATCHER.assertEquals(meal, service.get(meal.getId(), USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserMeal() {
        Meal meal = service.get(START_SEQ + 2, USER_ID);
        meal.setDescription("Обжорыш");
        meal.setCalories(2000);
        service.update(meal, ADMIN_ID);
    }
    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Перекус", 777);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        List<Meal> expected = new ArrayList<>(MEALS_FOR_USER);
        expected.add(0, newMeal);
        MATCHER.assertCollectionEquals(expected, service.getAll(USER_ID));
    }

}