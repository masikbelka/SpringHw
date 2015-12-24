<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
${user.id} <br>
${user.name}<br>
${user.email}<br>
<form method="POST" action=<c:url value="/user/delete/${user.id}"/>>
    <input type="submit" value="delete">
</form>
</body>
</html>
