<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal List</title>
</head>
<body>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <table>
        <tr>
            <td>Дата и время</td>
            <td>Описание</td>
            <td>Калории</td>
        </tr>
        <c:forEach items="${requestScope.meals}" var="meal">
            <c:choose>
                <c:when test="${meal.exceed}">
                    <tr bgcolor="red">
                </c:when>
                <c:when test="${not meal.exceed}">
                    <tr bgcolor="green">
                </c:when>
            </c:choose>
                <td>${requestScope.dtf.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
