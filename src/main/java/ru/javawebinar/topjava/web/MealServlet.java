package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by ArtemAA on 26.03.2017.
 */
public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    //private static final

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(MealsUtil.getMeals(),
                LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        request.setAttribute("meals", list);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
        //resp.sendRedirect("meals.jsp");
    }

}
