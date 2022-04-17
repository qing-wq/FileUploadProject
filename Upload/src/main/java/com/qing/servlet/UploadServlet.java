package com.qing.servlet;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
    String fileName = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        System.out.println("username"+username);
        // �ļ��ϴ��ܵ�ַ
        String Path = "C:\\Users\\hujingjing\\Desktop\\fileUpload";
        String upPath = Path + File.separator + username;
        File uploadFile = new File(upPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        // multipartResolver:ȫ�ֵ��ļ��ϴ�������,����ȡ���ļ�����
        // CommonsMultipartResolver ���ڴ���post����
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        // ����html�ļ�
        uploadHtml(multipartRequest,upPath);

        // ע�⣺.assets�ļ���������html�ļ������ɵ�
        String folderName = fileName.substring(0, fileName.lastIndexOf(".") + 1) + ".assets";
        String folderPath = upPath + File.separator + folderName;
        File uploadFolder = new File(folderPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // �����ļ���
        if (fileName == null || fileName.trim().equals("")) {
            System.out.println("html�ļ�������");
            return;
        }
        uploadAssets(multipartRequest, folderPath);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }


    private void uploadHtml(MultipartHttpServletRequest multipartRequest, String upPath) throws IOException {
        MultipartFile file = multipartRequest.getFile("file");
        if (file != null) {
            fileName = file.getOriginalFilename();
            System.out.println("HTML-filename:" + fileName);
        } else {
            System.out.println("�����ϴ�html�ļ�");
            return;
        }
        String filePath = upPath + File.separator + file.getOriginalFilename();
        outPutFileStream(file,filePath);
    }

    private void uploadAssets(MultipartHttpServletRequest multipartRequest, String folderPath) throws IOException {
        List<MultipartFile> fileFolder = multipartRequest.getFiles("fileFolder");
        for (MultipartFile sonFile : fileFolder) {
            String sonFilename = sonFile.getOriginalFilename();
            String sonPath = folderPath + File.separator + sonFilename;
            outPutFileStream(sonFile,sonPath);
        }
    }

    private void outPutFileStream(MultipartFile file, String Path) throws IOException {
        InputStream inputStream = file.getInputStream();
        // �ж��Ƿ���html�ļ�
        if (Path.substring(Path.lastIndexOf(".") + 1).equals("html")) {
            inputStream = replaceUrl(inputStream);
        }
        FileOutputStream outputStream = new FileOutputStream(Path);
        byte[] buffer = new byte[1024 * 1024];
        int len =0;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer,0,len);
        }
        outputStream.close();
        inputStream.close();
    }

    private InputStream replaceUrl(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = -1;
        byte[] buffer = new byte[1024 * 1024];
        while ((len = inputStream.read(buffer))>0) {
            out.write(buffer,0,len);
        }
        String htmlContent = out.toString().replace("WCS", "HJJ");
//        System.out.println("�ļ�ԭ����Ϊ��" + out.toString());    // �������
//        System.out.println("�滻�������Ϊ��"+htmlContent);       // �������
        if (htmlContent.trim().equals("")) {
            System.out.println("�ı�����Ϊ��");
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}