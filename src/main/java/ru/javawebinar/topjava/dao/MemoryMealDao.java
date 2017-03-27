package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by ArtemAA on 27.03.2017.
 */
public class MemoryMealDao implements MealDao {

    private static AtomicLong counter = new AtomicLong(0);

    private static Map<Long, Meal> meals = new ConcurrentHashMap<>();

    public MemoryMealDao() {
        MealsUtil.getMeals().forEach(this::add);
    }

    @Override
    public void add(Meal meal) {
        meal.setId(counter.addAndGet(1));
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Meal meal) {

    }

    @Override
    public Meal get(long id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }
}
