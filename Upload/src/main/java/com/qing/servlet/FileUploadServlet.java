package com.qing.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
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
public class FileUploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 判断上传的表单中是否含有文件，不含则不必要处理
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }

        // 创建上传文件的保存路径，最好将上传的文件放下WEB-INF目录下，只能通过内部重定向和转发访问
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        String newPath = "C:\\Users\\hujingjing\\Desktop\\fileUpload";

        File uploadFile = new File(newPath);
        if (!uploadFile.exists()) {      // 如果没有这个目录，创建这个目录
            uploadFile.mkdir();
        }

        // 创建临时路径，存放缓存文件
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File tempFile = new File(tempPath);
        if (!tempFile.exists()) {
            tempFile.mkdir();
        }

        try {
            // =========创建DiskFileItemFactory对象，处理文件上传路径或者大小限制的=========
            DiskFileItemFactory factory = getDiskFileItemFactory(tempFile);   // 工厂模式
            // =========获取ServletFileUpload============
            ServletFileUpload upload = getServletFileUpload(factory);
            // ===========处理.html文件==============
            String fileName = uploadParseRequest(upload, newPath, request, response);
            // =============处理文件夹=================
            if (fileName.equals("")) {
                System.out.println("文件上传失败，可能原因如下：\n" +
                        "1. 没有先上传html文件再上传assets文件夹\n" +
                        "2. 上传的文件不是html文件\n"+
                        "3. 上传的文件名为空");
                return;
            }
            // 创建文件夹名称
            String fileFolderName = fileName.substring(0, fileName.lastIndexOf(".")) + ".assets";
            String fileFolderPath = newPath + File.separator + fileFolderName;
            // 创建.assets文件夹
            File fileFolder = new File(fileFolderPath);
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
            // 处理文件夹
//            String mes = uploadFileFolder(request, response, fileFolderPath);

            // servlet请求转发消息
//            request.setAttribute("mes", mes);
            HttpSession session = request.getSession();
            session.setAttribute("fileFolderName",fileFolderName);
            request.getRequestDispatcher("/UploadFileFolderServlet").forward(request, response);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File tempFile) {
        DiskFileItemFactory factory = new DiskFileItemFactory();    // 工厂模式
        // 通过工厂模式设置一个缓冲区，当文上传的文件超过这个缓冲区的大小时，将它放在临时目录中；
        factory.setSizeThreshold(1024 * 1024);   // 缓冲区的大小为1M
        factory.setRepository(tempFile);
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            @Override
            // pBytesRead:已经读取到的文件的大小
            // pContextLength:文件大小
            public void update(long pBytesRead, long pContentLength, int pItem) {
                long res = (pBytesRead * 100) / pContentLength;
                System.out.println("总大小：" + pContentLength + " 已上传：" + res + "%");
            }
        });

        // 处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        // 设置单个文件的最大值
        upload.setFileSizeMax(1024 * 1024 * 10);   // 10M
        // 设置总共能上传文件的大小
        upload.setSizeMax(1024 * 1024 * 10);   // 10M  1024=1kb  1kb*1024=1M
        return upload;
    }

    public static String uploadParseRequest(ServletFileUpload upload, String uploadPath, HttpServletRequest request, HttpServletResponse response) throws FileUploadException, IOException {
        String uploadFileName = "";
        List<FileItem> fileItems = upload.parseRequest(request);
        // 创建一个UUID文件夹，防止文件名重复
        String uuidPath = uuidPath = UUID.randomUUID().toString();
        for (FileItem fileItem : fileItems) {
            // 判断是否是表单中非文件上传的输入框
            if (fileItem.isFormField()) {
                String name = fileItem.getFieldName();    // getFileName()获取的是前端表单标签的name属性的值
                String value = fileItem.getString("UTF-8");   // 得到输入的值，并处理乱码
                System.out.println(name + ":" + value);
            } else if (fileItem.getFieldName().equals("file")) {
                // =========================处理文件============================
                uploadFileName = fileItem.getName();
                System.out.println("上传的文件为：" + uploadFileName);
                // 判断上传的文件名是否有问题
                if (uploadFileName.trim().equals("") || uploadFileName == null) {
                    continue;
                }

                // 获取上传的文件名
                String fileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
                // 获取上传文件的后缀
                String fileExName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);

                // 判断上传的文件是否合法
                if (!fileExName.equals("html")) {
                    System.out.println("上传的文件必须为.html文件，请重新上传");
                    return "";
                }

                // =======================存放文件===========================
                String realPath = uploadPath + "/" + uuidPath;
                File realPathFile = new File(realPath);
                if (!realPathFile.exists()) {
                    realPathFile.mkdirs();
                }

                // =======================文件替换并传输=========================
                // 获取文件上传的输入流
                InputStream inputStream = fileItem.getInputStream();
                String from = "WCS", to = "HJJ";
//                ContentReplace(inputStream,from,to);
                String fileContent = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
                // 如果str存在，那么进行替换
                if (!fileContent.equals("")) {
                    // 进行文件内容替换
                    fileContent = fileContent.replace(from, to);
                    // 将String转化为流
                    inputStream = new ByteArrayInputStream(fileContent.getBytes());
                }

                // 创建一个文件输出流
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
                return fileName;
            }
        }
        return "";
    }

//     文件夹上传
    private static String uploadFileFolder(HttpServletRequest request, HttpServletResponse response, String fileFolderPath) throws IOException {
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        List<MultipartFile> files = multipartRequest.getFiles("fileFolder");
        System.out.println("进入for循环前");
        // 遍历文件夹，处理子文件
        for (MultipartFile file : files) {
            System.out.println("当前文件名称为：" + file.getName());
//            if (!file.isEmpty()) {
            System.out.println("进入assets文件夹");
            // 转化成CommonsMultipartFile获取FileItem对象
            CommonsMultipartFile multipartFile = (CommonsMultipartFile) file;
            FileItem fileItem = multipartFile.getFileItem();
            String fileItemName = fileItem.getName();  // 获取到的为带路径的文件名
            String fileName = fileItemName.substring(fileItemName.lastIndexOf("/") + 1);   // 获取子文件名
            String fileExName = fileName.substring(fileName.lastIndexOf(".") + 1);     // 获取子文件后缀

            String realPath = fileFolderPath;
            // 获取文件输入流
            InputStream inputStream = fileItem.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileFolderPath + File.separator + fileName);
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
        return "文件上传成功";
    }

    private static void ContentReplace(InputStream inputStream, String from, String to) {
        // 获取文件内容
        String fileContent = new BufferedReader(new InputStreamReader(inputStream)).lines().parallel().collect(Collectors.joining(System.lineSeparator()));
        // 如果str存在，那么进行替换
        if (!fileContent.equals("")) {
            // 进行文件内容替换
//            fileContent = ContentReplace(fileContent, from, to);
            fileContent = fileContent.replace(from, to);
            // 将String转化为流
            inputStream = new ByteArrayInputStream(fileContent.getBytes());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
