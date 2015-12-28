<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<form method="post" action=<c:url value="/event/update"/>>
    <input type="hidden" name="id" value="${event.id}">
    <input type="text" name="title" id="title" value="${event.title}">
    <label for="date">Date</label>
    <input type="text" name="date" id="date" value=<fmt:formatDate value="${event.date.time}" pattern="yyyy-MM-dd" type="date" dateStyle="short" />>
    <label for="ticketPrice">Ticket price</label>
    <input type="text" name="ticketPrice" id="ticketPrice" value="${event.ticketPrice}">
    <input type="submit" value="submit">
</form>
</body>
</html>
