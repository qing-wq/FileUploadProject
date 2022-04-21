<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Select</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/forward">
    请输入要查询的文件：<input type="text" name="fileName" id="fileName" placeholder="请输入文件名" >
<%--    查看文件：序列化.html<br>--%>
    输入密码<input type="password" name="password" placeholder="我生日(写错了打死你！)" id="password"><br>
    <span id="message" style="color: red">${message}</span><br>
    <input type="submit" value="提交">
</form>
</body>
</html>
