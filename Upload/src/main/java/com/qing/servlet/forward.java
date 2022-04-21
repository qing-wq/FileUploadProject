package com.qing.servlet;

import sun.applet.Main;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/forward")
public class forward extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        String password = request.getParameter("password");
        if (password.equals("030825")) {
//            request.setAttribute("message","密码正确，正在跳转...");
            if (fileName.equals("序列化.html")) {
                response.sendRedirect(request.getServletContext().getRealPath("/upload/xlh.html"));
            } else if (fileName.equals("邮件发送.html")) {
                response.sendRedirect(request.getServletContext().getRealPath("/upload/yjfs.html"));
            } else {
                request.setAttribute("message","文件不存在");
            }
        } else{
//            request.setAttribute("message","自己生日都能写错，笨蛋！");
            request.setAttribute("message","密码错误!!!你完蛋了!!!");
            request.getRequestDispatcher("/Select.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
