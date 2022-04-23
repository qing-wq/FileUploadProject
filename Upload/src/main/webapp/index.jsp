<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>FileUpload</title>
  </head>
  <body>
<form action="${pageContext.request.contextPath}/upload.do" method="post" enctype="multipart/form-data">
  <p>上传html文件：<input type="file" name="file" id="file" ></p>
  <p>上传assets文件夹：<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>
<%--  <p><input type="button" name="btn" id="btn" value="保存数据"></p>--%>
  <p><input type="submit" value="提交" id="submit"><input type="reset" value="重置"></p>
</form>
  </body>
</html>
