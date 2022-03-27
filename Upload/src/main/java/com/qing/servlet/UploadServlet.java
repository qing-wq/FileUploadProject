package com.qing.servlet;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断上传的表单中是否含有文件，不含则不必要处理
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return;
//        }

//         String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");  获取当前路径

        // Problem:无法判断上传的表单是否含有文件

        String uploadPath =  "C:\\Users\\hujingjing\\Desktop\\fileUpload";

        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {      // 如果没有这个目录，创建这个目录
            uploadFile.mkdir();
        }

        // 获取MultipartHttpServletRequest对象
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);

        // 处理文件夹
        String fileName = uploadHtmlFile(request, multipartRequest, uploadPath);
        if (fileName.equals("")) {
            return;
        }
        String mes = uploadFileFolder(request,multipartRequest, uploadPath,fileName);

        // servlet请求转发消息
        request.setAttribute("mes", mes);
        request.getRequestDispatcher("/info.jsp").forward(request, response);
    }

    private String uploadHtmlFile(HttpServletRequest request,MultipartRequest multipartRequest, String uploadPath) throws IOException {

        MultipartFile HtmlFile = multipartRequest.getFile("file");
        String filename = "";
        if (HtmlFile != null) {
            filename = HtmlFile.getOriginalFilename();
        }else{
            System.out.println("请先上传html文件");
            return "";
        }
        CommonsMultipartFile htmlFile = (CommonsMultipartFile) HtmlFile;
        FileItem item = htmlFile.getFileItem();
        writeFile(item, filename, uploadPath, true);
        return filename;
    }

    //     文件夹上传
    private static String uploadFileFolder(HttpServletRequest request, MultipartRequest multipartRequest, String uploadPath, String filename) throws IOException {


        // ==================处理文件夹======================
        // 创建文件夹名称
        String fileFolderName = filename.substring(0, filename.lastIndexOf(".")) + ".assets";
        String fileFolderPath = uploadPath + File.separator + fileFolderName;
        // 创建.assets文件夹
        File fileFolder = new File(fileFolderPath);
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        List<MultipartFile> files = multipartRequest.getFiles("fileFolder");
        // 遍历文件夹，处理子文件
        for (MultipartFile file : files) {
//            System.out.println("当前文件名称为：" + file.getName());
            if (!file.isEmpty()) {
                System.out.println("进入assets文件夹");
                // 转化成CommonsMultipartFile获取FileItem对象
                CommonsMultipartFile multipartFile = (CommonsMultipartFile) file;
                FileItem fileItem = multipartFile.getFileItem();
                String fileItemName = fileItem.getName();  // 获取到的为带路径的文件名
                String fileName = fileItemName.substring(fileItemName.lastIndexOf("/") + 1);   // 获取子文件名

//                String realPath = fileFolderPath;
//                System.out.println(fileFolderPath);
                writeFile(fileItem, fileName, fileFolderPath, false);
            } else {
                System.out.println("[Warning]文件夹中的文件为空");
            }
        }
        return "文件上传成功";
    }

    private static void writeFile(FileItem item, String filename, String path, Boolean isHtmlFile) throws IOException {
        // 获取文件输入流
        InputStream inputStream = item.getInputStream();
        if(isHtmlFile){
            ContentReplace(filename,inputStream,path);
        }
        // 获取输出流
        FileOutputStream outputStream = new FileOutputStream(path + File.separator + filename);
        System.out.println(path + File.separator + filename);
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

    private static void ContentReplace(String filename,InputStream inputStream,String uploadPath) throws FileNotFoundException {
        // 内容替换
        String fileContent = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(System.lineSeparator()));
        if (!fileContent.equals("")) {
            fileContent.replaceAll(filename + ".asstes/*.img", uploadPath + filename + ".assets");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

