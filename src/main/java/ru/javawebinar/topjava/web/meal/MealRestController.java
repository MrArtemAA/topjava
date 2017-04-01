package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;

@Controller
public class MealRestController {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        LOG.info("create: " + meal);
        checkNew(meal);
        return service.save(AuthorizedUser.id(), meal);
    }

    public Meal get(int id) {
        LOG.info("get: " + id);
        return service.get(AuthorizedUser.id(), id);
    }

    public void update(Meal meal, int id) {
        LOG.info("update: " + meal);
        checkIdConsistent(meal, id);
        service.save(AuthorizedUser.id(), meal);
    }

    public void delete(int id) {
        LOG.info("delete: " + id);
        service.delete(AuthorizedUser.id(), id);
    }

    public Collection<MealWithExceed> getAll() {
        LOG.info("getAll:");
        Collection<MealWithExceed> result = MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
        LOG.info(result.size() + ": " + result.toString());
        return result;
    }

    public Collection<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        startDate = Optional.ofNullable(startDate).orElse(LocalDate.MIN);
        startTime = Optional.ofNullable(startTime).orElse(LocalTime.MIN);
        endDate = Optional.ofNullable(endDate).orElse(LocalDate.MAX);
        endTime = Optional.ofNullable(endTime).orElse(LocalTime.MAX);

        LOG.info("getFiltered: ", startDate, startTime, endDate, endTime);
        Collection<MealWithExceed> result = MealsUtil.getWithExceeded(service.getFiltered(AuthorizedUser.id(),
                startDate, startTime, endDate, endTime), AuthorizedUser.getCaloriesPerDay());
        LOG.info(result.size() + ": " + result.toString());
        return result;
    }

}