<%@ page contentType="text/html;charset=GBK" language="java" %>

<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
<form action="${pageContext.request.contextPath}/upload.do" method="post" enctype="multipart/form-data">
  <p>�ϴ�html�ļ���<input type="file" name="file" id="file" ></p>
  <p>�ϴ�assets�ļ��У�<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory> </p>
<%--  <p><input type="button" name="btn" id="btn" value="��������"></p>--%>
  <p><input type="submit" value="�ύ" id="submit"><input type="reset" value="����"></p>
</form>
<%--<div>--%>
<%--  <p>�û�����<input type="text" name="username" id="username" placeholder="����д�û���"></p>--%>
<%--  <p>�ϴ�html�ļ���<input type="file" name="file" id="file"/></p>--%>
<%--  <p>�ϴ�assets�ļ��У�<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory>--%>
<%--</div>--%>
<%--<div>--%>
<%--  <button id="mybtn">�ύ</button>--%>
<%--</div>--%>
<%--<div id="div">--%>
<%--</div>--%>
<%--<script src="https://code.jquery.com/jquery-3.4.1.js"></script>--%>
<%--<script>--%>
<%--  //��ȡһ���ϴ��ļ�����չ��--%>
<%--  var myfile = document.getElementById('file');--%>
<%--  var mybtn = document.getElementById('btn');--%>
<%--  var username = $("#username").val();--%>
<%--  var file = new FormData();--%>
<%--  file.append("myFile", $("#file")[0].files[0]);--%>
<%--  var fileFolder = $("#fileFolder");--%>
<%--  for (fileFolder.has)--%>
<%--  mybtn.onclick = function () {--%>
<%--      $('button').click(function () {--%>
<%--        $.ajax({--%>
<%--          url: "/FileUploadProject/upload.do",--%>
<%--          //post����--%>
<%--          method: "post",--%>
<%--          //����ȥ������--%>
<%--          data:{--%>
<%--            filename: JSON.stringify(myfile.files[0].name),--%>
<%--            username: JSON.stringify(username),--%>
<%--            file: JSON.stringify(file),--%>
<%--            fileFolder :JSON.stringify(fileFolder)--%>
<%--          }--%>
<%--        })--%>
<%--      })--%>
<%--    }--%>
</script>
  </body>
</html>
