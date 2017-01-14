package dbawba.controllers;

import dbawba.data.User;
import dbawba.tools.Globals;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
        rd.forward(request, response);
    }
    
    /**
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Received parameters
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String rememberMe = request.getParameter("rememberMe");
        
        if (!User.isThereTheUser(Globals.fixSQLString(username),Globals.getHashCode_MD5_Algorithm(pass)))
        {
            request.getSession().setAttribute("message", "Μη έγκυρο username ή password!");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
        else
        {
            User newUser = new User();
            newUser.loadUserFromDB(username) ;
            request.getSession().setAttribute("User", newUser);
            
            if (rememberMe != null && rememberMe.toUpperCase().equals("YES"))
            {
                Cookie usernameCookie = new Cookie("username", username);
                usernameCookie.setMaxAge(172800); // Two days
                response.addCookie(usernameCookie);
                Cookie passwordCookie = new Cookie("password", Globals.getHashCode_MD5_Algorithm(pass));
                passwordCookie.setMaxAge(172800); // Two days
                response.addCookie(passwordCookie);
            }
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.forward(request, response);
        }
    }
}
