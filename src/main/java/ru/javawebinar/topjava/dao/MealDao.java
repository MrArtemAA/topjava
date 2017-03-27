package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by ArtemAA on 27.03.2017.
 */
public interface MealDao {

    void add(Meal meal);
    void delete(long id);
    void update(Meal meal);
    Meal get(long id);
    List<Meal> getAll();

}
