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
<%--<div>--%>
<%--  <p>用户名：<input type="text" name="username" id="username" placeholder="请填写用户名"></p>--%>
<%--  <p>上传html文件：<input type="file" name="file" id="file"/></p>--%>
<%--  <p>上传assets文件夹：<input id="fileFolder" name="fileFolder" type="file"  webkitdirectory>--%>
<%--</div>--%>
<%--<div>--%>
<%--  <button id="mybtn">提交</button>--%>
<%--</div>--%>
<%--<div id="div">--%>
<%--</div>--%>
<%--<script src="https://code.jquery.com/jquery-3.4.1.js"></script>--%>
<%--<script>--%>
<%--  //获取一个上传文件的扩展名--%>
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
<%--          //post方法--%>
<%--          method: "post",--%>
<%--          //传过去的数据--%>
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
