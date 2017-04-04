package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealFilter;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            mealRestController = appCtx.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request. getParameter("action");
        switch (action == null ? "update" : action) {
            case "update":
                postMeal(request);
                break;

            case "filter":
                postFilter(request, response);
                break;
        }

        response.sendRedirect("meals");
    }

    protected void postMeal(HttpServletRequest request) {
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id), AuthorizedUser.getId(),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew())
            mealRestController.create(meal);
        else {
            mealRestController.update(meal, Integer.valueOf(id));
        }
    }

    protected void postFilter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealFilter filter = new MealFilter();
        filter.setStartDate(getDate(request.getParameter("startDate")));
        filter.setEndDate(getDate(request.getParameter("endDate")));

        filter.setStartTime(getTime(request.getParameter( "startTime")));
        filter.setEndTime(getTime(request.getParameter("endTime")));

        LOG.info("getFiltered");

        request.setAttribute("filter", filter);
        request.setAttribute("meals",
                mealRestController.getFiltered(filter.getStartDate(), filter.getStartTime(),
                        filter.getEndDate(), filter.getEndTime()));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    protected LocalDate getDate(String paramValue) {
        if (paramValue == null || paramValue.equals(""))
            return null;
        else
            return LocalDate.parse(paramValue);
    }

    protected LocalTime getTime(String paramValue) {
        if (paramValue == null || paramValue.equals(""))
            return null;
        else
            return LocalTime.parse(paramValue);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                LOG.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = action.equals("create") ?
                        new Meal(AuthorizedUser.getId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/meal.jsp").forward(request, response);
                break;
            case "all":
            default:
                LOG.info("getAll");
                request.setAttribute("meals",
                        mealRestController.getAll());
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}