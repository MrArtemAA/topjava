package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by ArtemAA on 24.04.2017.
 */
@ActiveProfiles(Profiles.JDBC)
public class JdbcMealServiceTest extends MealServiceTest {

    @BeforeClass
    public static void setLog() {
        LOG = getLogger(JdbcMealServiceTest.class);
    }

}
