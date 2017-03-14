package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(10, 30), LocalTime.of(20, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesByDate;
        caloriesByDate = mealList.stream()
                .collect(Collectors.toMap());

        /*
        mealList.forEach((UserMeal userMeal) -> {
            LocalDate date = userMeal.getDateTime().toLocalDate();

            //Integer value = caloriesByDate.getOrDefault(date, 0);
            //caloriesByDate.put(date, value + userMeal.getCalories());
            caloriesByDate.merge(date, userMeal.getCalories(), (value, newValue) -> value + newValue);
        });
        */

        return mealList.stream()
                .filter(userMeal -> {
                    LocalTime time = userMeal.getDateTime().toLocalTime();
                    return TimeUtil.isBetween(time, startTime, endTime);
                })
                .map(userMeal -> {
                    LocalDate date = userMeal.getDateTime().toLocalDate();
                    UserMealWithExceed userMealWithExceed = new UserMealWithExceed(userMeal, caloriesByDate.get(date) > caloriesPerDay);
                    System.out.println(userMealWithExceed);
                    return userMealWithExceed;
                })
                .collect(Collectors.toList());
    }
}
