<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book ticket</title>
</head>
<body>
<form method="POST" action=<c:url value="/ticket/book"/>>
    <input type="hidden" name="eventId" value="${eventId}">
    <label for="userId">User id</label>
    <input type="text" name="userId" id="userId">
    <label for="category">Category</label>
    <select name="category" id="category" >
        <c:forEach var="ticketCategory" items="${categories}">
            <option value="${ticketCategory.value()}">${ticketCategory}</option>
        </c:forEach>
    </select>
    <label for="place">Place</label>
    <input type="text" name="place" id="place">
    <input type="submit" value="submit">
</form>
</body>
</html>
