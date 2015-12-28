<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
${event.id}<br>
${event.title}<br>
<fmt:formatDate value="${event.date.time}" type="date" dateStyle="short" /><br>
${event.ticketPrice}<br>
<form method="POST" action=<c:url value="/event/delete/${event.id}"/>>
    <input type="submit" value="delete">
</form>
<form method="GET" action=<c:url value="/event/edit/${event.id}"/>>
    <input type="submit" value="edit">
</form>
<form method="GET" action=<c:url value="/ticket/event/${event.id}"/>>
    <input type="submit" value="book ticket">
</form>
</body>
</html>
