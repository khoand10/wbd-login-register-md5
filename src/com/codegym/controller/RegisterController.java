package com.codegym.controller;

import com.codegym.model.User;
import com.codegym.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterController", urlPatterns = "/register")
public class RegisterController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("btn_register") != null) //check button click event not null from register.jsp page button
        {
            String firstname = request.getParameter("txt_firstname");
            String lastname = request.getParameter("txt_lastname");
            String username = request.getParameter("txt_username");  //get all textbox name from register.jsp page
            String password = request.getParameter("txt_password");

            User user = new User(); //this class contain  seeting up all received values from register.jsp page to setter and getter method for application require effectively

            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setUsername(username);  //set the all value through registerBean object
            user.setPassword(password);

            UserService registerdao = new UserService(); //this class contain main logic to perform function calling and database operation

            String registerValidate = registerdao.authorizeRegister(user); //send registerBean object values into authorizeRegister() function in RegisterDao class

            if (registerValidate.equals("SUCCESS REGISTER")) //check calling authorizeRegister() function receive "SUCCESS REGISTER" string message after redirect to index.jsp page
            {
                request.setAttribute("RegiseterSuccessMsg", registerValidate); //apply register successfully message "RegiseterSuccessMsg"
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp"); //redirect to index.jsp page
                rd.forward(request, response);
            } else {
                request.setAttribute("RegisterErrorMsg", registerValidate); // apply register error message "RegiseterErrorMsg"
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp"); //show error same page register.jsp page
                rd.include(request, response);
            }
        }
    }
}
