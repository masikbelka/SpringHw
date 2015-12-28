<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table cellpadding="10px" border="1px">
    <tr>
        <th>Title</th>
        <th>Date</th>
        <th>Price</th>
    </tr>
    <c:forEach var="event" items="${events}">
        <tr>
            <td>${event.title}</td>
            <td><fmt:formatDate value="${event.date.time}" type="date" dateStyle="short"/></td>
            <td>${event.ticketPrice}$</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
