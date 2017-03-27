<%--
  User: ArtemAA
  Date: 27.03.2017
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<form method="POST" action='mealController' name="formCreateMeal">
    ID : <input type="text" readonly="readonly" name="id"
                value="${meal.id}"/> <br/>
    Date and Time : <input
        type="datetime-local" name="dateTime"
        value="${not empty meal.dateTime ? dtf.format(meal.dateTime) : ''}"/> <br/>
    Description : <input
        type="text" name="description"
        value="${meal.description}"/> <br/>
    Calories : <input
        type="number" name="calories"
        value="${meal.calories}"/><br />
    <input
        type="submit" value="Submit" />
</form>
</body>
</html>
