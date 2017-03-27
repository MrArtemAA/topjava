package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

/**
 * Created by ArtemAA on 26.03.2017.
 */
public class MealServlet extends HttpServlet {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(MealsUtil.getMeals(),
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        request.setAttribute("meals", list);
        request.setAttribute("dtf", dtf);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
