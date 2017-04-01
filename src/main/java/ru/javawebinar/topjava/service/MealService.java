package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {

    Meal save(int userId, Meal meal);

    Meal get(int userId, int id) throws NotFoundException;

    void update(int usetId, Meal meal);

    void delete(int userId, int id) throws NotFoundException;

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime);

}