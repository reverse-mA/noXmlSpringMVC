<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>错误提示</title>
</head>
<body>
    <font color="red"><c:out value="${message.content}"/></font>
    <br>
    <a href="login.html">back to login</a>
</body>
</html>
