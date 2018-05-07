package com.codegym.service;

import com.codegym.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    public String authorizeLogin(User user) {
        String username = user.getUsername(); //get username value through loginBean object and store in temporary variable "username"
        String password = user.getPassword(); //get password value through loginBean object and store in temporary variable "password"

        String dbusername = "";  //create two variable for use next process
        String dbpassword = "";

        String url = "jdbc:mysql://localhost:3306/mydb"; //database connection url string
        String uname = "root"; //database username
        String pass = "123456"; //database password

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "123456", "mydb", "3306");

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("select * from user where username=? and password=?"); //sql select query
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery(); //execute query and set in Resultset object rs.

            while (rs.next()) {
                dbusername = rs.getString("username");   //fetchable database record username and password store in this two variable dbusername,dbpassword above created
                dbpassword = rs.getString("password");

                if (username.equals(dbusername) && password.equals(dbpassword))  //apply if condition check for fetchable database username and password are match for user input side type in textbox
                {
                    return "SUCCESS LOGIN"; //if valid condition return string "SUCCESS LOGIN"
                }
            }

            pstmt.close(); //close statement

            con.close(); //close connection

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "WRONG USERNAME AND PASSWORD"; //if invalid condition return string "WRONG USERNAME AND PASSWORD"
    }

    public String authorizeRegister(User user) {
        String firstname = user.getFirstname();
        String lastname = user.getLastname();
        String username = user.getUsername();  //get all value through registerBean object and store in temporary variable
        String password = user.getPassword();

        String url = "jdbc:mysql://localhost:3306/mydb"; //database connection url string
        String uname = "root"; //database username
        String pass = "123456"; //database password

        try {
            Connection con = ConnectionUtil.getConnection("localhost", "root", "123456", "mydb", "3306");

            PreparedStatement pstmt = null; //create statement

            pstmt = con.prepareStatement("insert into user(firstname,lastname,username,password) values(?,?,?,md5(?))"); //sql insert query
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.executeUpdate(); //execute query

            pstmt.close(); //close statement
            con.close(); //close connection
            return "SUCCESS REGISTER"; //if valid return string "SUCCESS REGISTER"
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL REGISTER"; //if invalid return string "FAIL REGISTER"
    }
}
