package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MemoryMealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by ArtemAA on 27.03.2017.
 */
public class MealController extends HttpServlet {

    private static final String CREATE_UPDATE = "/meal.jsp";
    private static final String LIST = "/meals.jsp";
    private static final String LIST_REDIRECT = "mealController";

    private static MealDao dao = new MemoryMealDao();

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean forward = true;
        String forwardPage = CREATE_UPDATE;
        String action = request.getParameter("action");

        if (action == null) {
            forwardPage = LIST;

            List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.getAll(),
                    LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("dtf", dtf);
            request.setAttribute("meals", list);

        } else if (action.equalsIgnoreCase("delete")){
            forward = false;

            long id = Long.parseLong(request.getParameter("id"));
            dao.delete(id);

        } else if (action.equalsIgnoreCase("update")) {
            long id = Long.parseLong(request.getParameter("id"));

            Meal meal = dao.get(id);
            request.setAttribute("dtf", dtf);
            request.setAttribute("meal", meal);

        }

        if (forward)
            request.getRequestDispatcher(forwardPage).forward(request, response);
        else
            response.sendRedirect(LIST_REDIRECT);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.valueOf(request.getParameter("calories"));

        Meal meal = new Meal(dateTime, description, calories);
        String id = request.getParameter("id");
        if(id == null || id.isEmpty()) {
            dao.add(meal);
        } else {
            meal.setId(Long.parseLong(id));
            dao.update(meal);
        }

        response.sendRedirect(LIST_REDIRECT);
    }

    private List<MealWithExceed> getMeals() {
        return MealsUtil.getFilteredWithExceeded(dao.getAll(),
                LocalTime.MIN, LocalTime.MAX, 2000);
    }

}
