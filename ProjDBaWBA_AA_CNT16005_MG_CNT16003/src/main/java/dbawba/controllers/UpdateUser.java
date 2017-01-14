package dbawba.controllers;

import dbawba.data.User;
import dbawba.tools.Globals;
import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
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
        String isThereEditableUser = request.getParameter("editableUser");
        String type = request.getParameter("type");
        User user ;
        if (isThereEditableUser != null && isThereEditableUser.toUpperCase().equals("YES"))
        {
            Globals.checkUser(request,response,true);
            user = (User) request.getSession().getAttribute("EditableUser");
        }
        else
            user = Globals.checkUser(request,response);
        //Received parameters
        String password1 = request.getParameter("Password1");
        String password2 = request.getParameter("Password2");
        String name = request.getParameter("Name");
        String surname = request.getParameter("Surname");
        String email = request.getParameter("Email");
        boolean isThereError = false ;
        if (password1.trim().equals("") || password2.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για το Password!");
            isThereError = true ;
        }
        else if (email.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για το Email!");
            isThereError = true ;
        }
        else if (!password1.equals(password2))
        {
            request.getSession().setAttribute("message", "Δεν έχετε συμπληρώσει το ίδιο Password.");
            isThereError = true ;
        }
        if (isThereError)
        {
            RequestDispatcher rd = request.getRequestDispatcher("EditProfile.jsp");
            rd.forward(request, response);
        }
        else
        {
            if (!password1.equals("*$@#*&HGF**"))
                user.setPassword(Globals.getHashCode_MD5_Algorithm(password1));
            user.setName(Globals.fixSQLString(name));
            user.setSurname(Globals.fixSQLString(surname));
            user.setEmail(Globals.fixSQLString(email));
            if (isThereEditableUser != null && isThereEditableUser.toUpperCase().equals("YES") && type !=null)
                user.setType(Globals.fixSQLString(type));
            if (user.updateUserToDB())
            {
                if (isThereEditableUser != null && isThereEditableUser.toUpperCase().equals("YES"))
                {
                    request.getSession().setAttribute("message", "Έγιναν επιτυχώς οι αλλαγές!");
                    RequestDispatcher rd = request.getRequestDispatcher("manageUsers.jsp");
                    rd.forward(request, response);
                }
                else
                {
                    request.getSession().setAttribute("message", "Έγιναν επιτυχώς οι αλλαγές!");
                    RequestDispatcher rd = request.getRequestDispatcher("profile.jsp");
                    rd.forward(request, response);
                }
            }
            else
            {
                request.getSession().setAttribute("message", "Σφάλμα κατά την αλλαγή των στοιχείων του χρήστη!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
            }
        }
    }
}
