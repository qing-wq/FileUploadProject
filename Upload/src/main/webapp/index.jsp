<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
<form action="${pageContext.request.contextPath}/upload.do" method="post" enctype="multipart/form-data">
  <p>�û�����<input type="text" name="username" placeholder="����д�û���"></p>
  <p>�ϴ�html�ļ���<input type="file" name="file" ></p>
  <p>�ϴ�assets�ļ��У�<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>
  <p><input type="submit" value="�ύ"><input type="reset" value="����"></p>
</form>
<%--<form action="${pageContext.request.contextPath}/UploadFileFolderServlet" method="post" enctype="multipart/form-data">--%>
<%--  <p>�ϴ��ļ��У�<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>--%>
<%--  <p><input type="submit" value="�ύ"><input type="reset" value="����"></p>--%>
<%--</form>--%>
  </body>
</html>
