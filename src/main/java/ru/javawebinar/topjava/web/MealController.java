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

    private static MealDao dao = new MemoryMealDao();

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");

        request.setAttribute("dtf", dtf);

        if (action.equalsIgnoreCase("delete")){
            long id = Long.parseLong(request.getParameter("id"));
            dao.delete(id);
            forward = LIST;

            List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.getAll(),
                    LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", list);
        } else if (action.equalsIgnoreCase("update")){
            forward = CREATE_UPDATE;

            long id = Long.parseLong(request.getParameter("id"));
            Meal meal = dao.get(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("list")){
            forward = LIST;

            List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(dao.getAll(),
                    LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", list);
        } else {
            forward = CREATE_UPDATE;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

        request.setAttribute("dtf", dtf);
        request.setAttribute("meals", getMeals());
        request.getRequestDispatcher(LIST).forward(request, response);
    }

    private List<MealWithExceed> getMeals() {
        return MealsUtil.getFilteredWithExceeded(dao.getAll(),
                LocalTime.MIN, LocalTime.MAX, 2000);
    }

}
