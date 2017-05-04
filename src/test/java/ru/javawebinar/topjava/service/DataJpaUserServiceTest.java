package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by ArtemAA on 24.04.2017.
 */
@ActiveProfiles(Profiles.DATA_JPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @BeforeClass
    public static void setLog() {
        LOG = LoggerFactory.getLogger(DataJpaUserServiceTest.class);
    }

    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, user.getMeals());
    }

    @Test
    public void testGetWithNoMeals() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, Collections.singleton(Role.ROLE_USER));
        User created = service.save(newUser);
        User user = service.getWithMeals(created.getId());
        MATCHER.assertEquals(created, user);
        Assert.assertEquals(0, user.getMeals().size());
    }
}
