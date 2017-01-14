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

@WebServlet("/NewUser")
public class NewUser extends HttpServlet {
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
        String username = request.getParameter("UsernameR");
        String password1 = request.getParameter("Password1");
        String password2 = request.getParameter("Password2");
        String name = request.getParameter("Name");
        String surname = request.getParameter("Surname");
        String email = request.getParameter("Email");
        String captcha = request.getParameter("usercaptcha") ;
        
        //Get captcha
        String sysCaptcha = (String) request.getSession().getAttribute("captcha");
        
        boolean isThereError = false ;
        if (username.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για το username!");
            isThereError = true ;
        }
        else if (password1.trim().equals("") || password2.trim().equals(""))
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
        else if (!sysCaptcha.equals(captcha))
        {
            request.getSession().setAttribute("message", "Δεν συμπληρώσατε σωστό captcha.");
            isThereError = true ;
        }
        else if (User.isThereTheUser(username))
        {
            request.getSession().setAttribute("message", "Το username υπάρχει ήδη! Αλλάξτε το.");
            isThereError = true ;
        }
        
        if (isThereError)
        {
            RequestDispatcher rd = request.getRequestDispatcher("signUp.jsp?username="+username+"&name="+name+"&surname="+surname+"&email="+email);
            rd.forward(request, response);
        }
        else
        {
            User newUser = new User();
            
            newUser.setUsername(Globals.fixSQLString(username));
            newUser.setPassword(Globals.getHashCode_MD5_Algorithm(password1));
            newUser.setName(Globals.fixSQLString(name));
            newUser.setSurname(Globals.fixSQLString(surname));
            newUser.setType("U");
            newUser.setEmail(Globals.fixSQLString(email));
            
            if (newUser.insertNewUserToDB())
            {
                request.getSession().setAttribute("message", "Δημιουργήθηκε επιτυχώς ο νέος χρήστης!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/displayInfo.jsp");
                rd.forward(request, response);
            }
            else
            {
                request.getSession().setAttribute("message", "Σφάλμα κατά την δημιουργία νέου χρήστη!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
            }
        }
    }
}
