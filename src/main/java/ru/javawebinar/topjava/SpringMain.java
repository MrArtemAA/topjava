package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("\nBean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()) + "\n");
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            System.out.println(adminUserController.getAll());

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.getAll();
            mealRestController.getFiltered(LocalDate.of(2015, Month.MAY, 31), null, null, null);
            mealRestController.create(new Meal(1, LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 500));
            mealRestController.getFiltered(null, null, null, null);
        }
    }
}
