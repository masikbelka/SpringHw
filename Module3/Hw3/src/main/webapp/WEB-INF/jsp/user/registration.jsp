<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form method="post" action=<c:url value="/user/create"/>>
    <label for="name">Name</label>
    <input type="text" id="name" name="name"/>
    <label for="email">Email</label>
    <input type="text" id="email" name="email">
    <input type="submit" value="submit">
</form>
</body>
</html>
