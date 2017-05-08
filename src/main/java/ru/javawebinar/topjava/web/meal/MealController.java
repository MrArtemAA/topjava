package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * MrArtemAA
 * 06.05.2017
 */
@Controller
@RequestMapping(value = "/meals")
public class MealController extends MealRestController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        return createOrUpdate(model,
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String update(Model model, @PathVariable("id") int id) {
        return createOrUpdate(model, super.get(id));
    }

    private String createOrUpdate(Model model, Meal meal) {
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") int id) {
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:meals";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
