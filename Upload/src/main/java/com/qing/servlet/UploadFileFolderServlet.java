package com.qing.servlet;

import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import sun.nio.cs.ext.GBK;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@WebServlet("/UploadFileFolderServlet")
public class UploadFileFolderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String newPath = "C:\\Users\\hujingjing\\Desktop\\fileUpload";
        String fileFolderName = (String) request.getSession().getAttribute("fileFolderName");
        File uploadFile = new File(newPath + "/" + fileFolderName);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        String mes = UploadFileFolder(request, response, newPath, fileFolderName);
        request.setAttribute("mes", mes);
        request.getRequestDispatcher("/info.jsp").forward(request, response);
    }

    private String UploadFileFolder(HttpServletRequest request, HttpServletResponse response, String newPath, String fileFolderName) throws IOException {

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        List<MultipartFile> files = multipartRequest.getFiles("fileFolder");
        System.out.println(files.size());
        System.out.println(files==null);
        for (MultipartFile file : files) {
//             获取文件夹中的文件的相对路径
//            try {
//                if (file instanceof CommonsMultipartFile) {
//                    CommonsMultipartFile f2 = (CommonsMultipartFile) file;
//                    String name = new String(((CommonsMultipartFile) file).getFileItem().getName().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//                    System.out.println(name + "\t\t---相对路径");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            if (!file.isEmpty()) {
                // 用CommonsMultipartFile转化成FileItem对象
                CommonsMultipartFile multipartFile = (CommonsMultipartFile) file;
                FileItem fileItem = multipartFile.getFileItem();
                String fileItemName = fileItem.getName();  // 获取到的为带路径的文件名
                String fileName = fileItemName.substring(fileItemName.lastIndexOf("/") + 1);
                String fileExName = fileName.substring(fileName.lastIndexOf(".") + 1);

                String realPath = newPath + File.separator + fileFolderName;
                // 获取文件输入流
                InputStream inputStream = fileItem.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(realPath + "/" + fileName);
                // 创建一个缓冲区
                byte[] buffer = new byte[1024 * 1024];
                // 判断是否读取完毕
                int len = 0;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                // 关闭流
                outputStream.close();
                inputStream.close();
            }
        }
        return "文件夹上传成功";
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
