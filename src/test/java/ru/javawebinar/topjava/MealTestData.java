package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    public static final List<Meal> MEALS_FOR_USER = Arrays.asList(
            new Meal(START_SEQ + 2, LocalDateTime.of(2017, Month.APRIL, 8, 18, 0), "Ужин", 500),
            new Meal(START_SEQ + 3, LocalDateTime.of(2017, Month.APRIL, 8, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 4, LocalDateTime.of(2017, Month.APRIL, 8, 7, 0), "Завтрак", 500)
    );

    public static final List<Meal> MEALS_FOR_ADMIN = Arrays.asList(
            new Meal(START_SEQ + 5, LocalDateTime.of(2017, Month.APRIL, 9, 18, 0), "Ужин", 1000),
            new Meal(START_SEQ + 6, LocalDateTime.of(2017, Month.APRIL, 9, 13, 0), "Обед", 510),
            new Meal(START_SEQ + 7, LocalDateTime.of(2017, Month.APRIL, 9, 7, 0), "Завтрак", 500)
    );

    public static final LocalDateTime DATE1 = LocalDateTime.of(2017, Month.APRIL, 8, 13,0);
    public static final LocalDateTime DATE2 = LocalDateTime.of(2017, Month.APRIL, 8, 20,0);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            ((expected, actual) -> expected == actual || expected.toString().equals(actual.toString()))
    );

}
