package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginController", urlPatterns = "/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("btn_login") != null) //check button click event not null from login.jsp page button
        {
            String username = request.getParameter("txt_username"); //get textbox name "txt_username"
            String password = request.getParameter("txt_password"); //get textbox name "txt_password"

            User user = new User(); //this class contain seeting up all received values from index.jsp page to setter and getter method for application require effectively

            user.setUsername(username); //set username through loginBean object
            user.setPassword(password); //set password through loginBean object

            UserService loginDao = new UserService(); //this class contain main logic to perform function calling and database operation

            String authorize = loginDao.authorizeLogin(user); //send loginBean object values into authorizeLogin() function in LoginDao class

            if (authorize.equals("SUCCESS LOGIN")) //check calling authorizeLogin() function receive string "SUCCESS LOGIN" message after continue process
            {
                HttpSession session = request.getSession(); //session is created
                session.setAttribute("login", user.getUsername()); //session name is "login" and  store username in "getUsername()" get through loginBean object
                RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp"); //redirect to welcome.jsp page
                rd.forward(request, response);
            } else {
                request.setAttribute("WrongLoginMsg", authorize); //wrong login error message is "WrongLoginMsg"
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //show error same index.jsp page
                rd.include(request, response);
            }
        }
    }
}
