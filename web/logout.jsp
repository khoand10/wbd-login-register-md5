<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<%
    session.invalidate(); //session destroy
    response.sendRedirect("index.jsp"); //after destroy redirect to index.jsp page
%>
</body>
</html>
