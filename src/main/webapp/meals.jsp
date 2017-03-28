<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal List</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <p><a href="mealController?action=create">Add meal</a></p>
    <table border="1">
        <tr>
            <td>Дата и время</td>
            <td>Описание</td>
            <td>Калории</td>
            <td></td>
            <td></td>
        </tr>
        <c:forEach items="${requestScope.meals}" var="meal">
            ${meal.exceed ? '<tr bgcolor="red">' : '<tr bgcolor="green">'}
                <td>${requestScope.dtf.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="mealController?action=update&id=${meal.id}">Edit</a> </td>
                <td><a href="mealController?action=delete&id=${meal.id}">Delete</a> </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
