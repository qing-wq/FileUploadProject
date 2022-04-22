<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<%=request.getAttribute("msc")%>--%>
<%--<%=request.getAttribute("msc2")%>--%>
<%=request.getAttribute("mes")%>
<p>===============================================================</p>
<p>已上传文件</p>
<c:forEach items="${requestScope.fileList}" var="keyword" varStatus="id">
    ${id.index} ${keyword.getFileName()} ${keyword.getFilePath()}<br>
</c:forEach>
</body>
</html>
