<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
<form action="${pageContext.request.contextPath}/upload.do" method="post" enctype="multipart/form-data">
  <p>用户名：<input type="text" name="username" placeholder="请填写用户名"></p>
  <p>上传html文件：<input type="file" name="file" ></p>
  <p>上传assets文件夹：<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>
  <p><input type="submit" value="提交"><input type="reset" value="重置"></p>
</form>
<%--<form action="${pageContext.request.contextPath}/UploadFileFolderServlet" method="post" enctype="multipart/form-data">--%>
<%--  <p>上传文件夹：<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>--%>
<%--  <p><input type="submit" value="提交"><input type="reset" value="重置"></p>--%>
<%--</form>--%>
  </body>
</html>
