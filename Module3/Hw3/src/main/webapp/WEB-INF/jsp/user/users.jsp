<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
  <tr>
    <th>Name</th>
    <th>Email</th>
  </tr>
<c:forEach var="user" items="${users}">
  <tr>
    <td>${user.name}</td>
    <td>${user.email}</td>
  </tr>
</c:forEach>
</table>
</body>
</html>
