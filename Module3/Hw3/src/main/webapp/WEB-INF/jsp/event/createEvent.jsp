<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create event</title>
</head>
<body>
<form method="post" action=<c:url value="/event/create"/>>
    <label for="title">Title</label>
    <input type="text" name="title" id="title">
    <label for="date">Date</label>
    <input type="text" name="date" id="date">
    <label for="ticketPrice">Ticket price</label>
    <input type="text" name="ticketPrice" id="ticketPrice">
    <input type="submit" value="submit">
</form>
</body>
</html>
