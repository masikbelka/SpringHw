<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit</title>
</head>
<body>
<form method="post" action=<c:url value="/user/update"/>>
  <input type="hidden" name="id" value="${user.id}">
  <label for="name">Name</label>
  <input type="text" name="name" id="name" value="${user.name}">
  <label for="email">Email</label>
  <input type="text" name="email" id="email" value="${user.email}">
  <input type="submit" value="submit">
</form>
</body>
</html>
