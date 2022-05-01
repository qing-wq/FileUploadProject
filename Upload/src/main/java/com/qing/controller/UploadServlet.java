package com.qing.controller;

import com.qing.entity.MyFile;
import com.qing.service.UserService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
    String fileName = "";
    MyFile myFile;
    String mes = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        myFile = new MyFile();
        // 文件上传总地址
        String upPath = this.getServletContext().getRealPath("/upload");
        System.out.println(upPath);
        File uploadFile = new File(upPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        uploadHtml(multipartRequest, upPath);
        if (!mes.equals("")) {
            request.getRequestDispatcher("/info.jsp").forward(request, response);
            return;
        }

        // 处理文件夹
        if (fileName == null || fileName.trim().equals("")) {
            System.out.println("html文件名出错！");
            return;
        }
        // 此处.assets文件夹名是由html文件名生成的
        String folderName = fileName.substring(0, fileName.lastIndexOf(".")) + ".assets";
        String folderPath = upPath + File.separator + folderName;
        File uploadFolder = new File(folderPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        uploadAssets(multipartRequest, folderPath);
        mes = "文件上传成功";
        request.setAttribute("mes",mes);
        UserService userService = new UserService(myFile);
        try {
            List<MyFile> list = userService.save();
            if (list.size() == 0) System.out.println("List is null");
            else request.setAttribute("fileList",list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/info.jsp").forward(request, response);
    }

    private void uploadHtml(MultipartHttpServletRequest multipartRequest, String upPath) throws IOException {
        MultipartFile file = multipartRequest.getFile("file");
        if (file != null) {
            fileName = file.getOriginalFilename();
            System.out.println("HTML-filename:" + fileName);
            if (fileName.substring(fileName.lastIndexOf(".") + 1).equals("html")) {
                System.out.println("Not HtmlFile");
                mes = "请上传html文件";
                return;
            }
        } else {
            System.out.println("html-File is null");
            return;
        }
        String filePath = upPath + File.separator + file.getOriginalFilename();
        myFile.setFilePath(filePath);
        myFile.setFileName(fileName);
        outPutFileStream(file, filePath);
    }

    private void uploadAssets(MultipartHttpServletRequest multipartRequest, String folderPath) throws IOException {
        List<MultipartFile> fileFolder = multipartRequest.getFiles("fileFolder");
        for (MultipartFile sonFile : fileFolder) {
            String sonFilename = sonFile.getOriginalFilename();
            String sonPath = folderPath + File.separator + sonFilename;
            outPutFileStream(sonFile, sonPath);
        }
    }

    private void outPutFileStream(MultipartFile file, String Path) throws IOException {
        InputStream inputStream = file.getInputStream();
        if (Path.substring(Path.lastIndexOf(".") + 1).equals("html")) {
            inputStream = replaceUrl(inputStream);
        }
        FileOutputStream outputStream = new FileOutputStream(Path);
        byte[] buffer = new byte[1024 * 1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
    }

    private InputStream replaceUrl(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024 * 1024];
        while ((len = inputStream.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        String htmlContent = out.toString().replace("wcs","hjj");
        if (htmlContent.trim().equals("")) {
            System.out.println("文本内容为空");
        }
        return new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}