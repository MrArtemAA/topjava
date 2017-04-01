package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            if (userId != meal.getUserId())
                return null;
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal meal = get(userId, id);
        if (meal == null) return false;

        repository.remove(id);
        return true;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId() == userId)
            return meal;
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFiltered(userId, LocalDate.MIN, LocalTime.MIN, LocalDate.MAX, LocalTime.MAX);
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return repository.values().stream()
                .filter(meal ->
                    meal.getUserId() == userId && DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)
                            && DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)
                )
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());
    }
}

