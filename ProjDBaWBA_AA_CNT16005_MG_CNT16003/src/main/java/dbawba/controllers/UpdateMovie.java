package dbawba.controllers;

import dbawba.data.Movie;
import dbawba.data.User;
import dbawba.tools.Globals;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateMovie")
public class UpdateMovie extends HttpServlet {
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
        User user = Globals.checkUser(request,response);
        
        String mID = request.getParameter("mID") ;
        if (mID==null || !Globals.isNumeric(mID) || !Movie.isThereTheID(Integer.parseInt(mID)))
        {
            request.getSession().setAttribute("message", "Σφάλμα!!! Κατά την τροποποίηση ταινιας!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
        Movie editableMovie = new Movie() ;
        editableMovie.loadMovieFromDB(Integer.parseInt(mID));
        if (editableMovie.getUserID()!=user.getUserID())
        {
            request.getSession().setAttribute("message", "Δεν έχετε τα απαραίτητα δικαιώματα για αυτήν την ταινία!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
        
        //Received parameters
        String title = request.getParameter("Title");
        String director = request.getParameter("Director");
        int category = Integer.parseInt(request.getParameter("categories"));
        
        boolean isThereError = false ;
        if (title.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για τον τίτλο!");
            isThereError = true ;
        }
        
        if (isThereError)
        {
            RequestDispatcher rd = request.getRequestDispatcher("editMovie.jsp");
            rd.forward(request, response);
        }
        else
        {
            editableMovie.setTitle(Globals.fixSQLString(title));
            editableMovie.setDirector(Globals.fixSQLString(director));
            editableMovie.setCategory(category);
            
            if (editableMovie.updateMovieToDB())
            {
                request.getSession().setAttribute("message", "Έγιναν επιτυχώς οι αλλαγές στην ταινία!");
                RequestDispatcher rd = request.getRequestDispatcher("editMovie.jsp?id="+mID);
                rd.forward(request, response);
            }
            else
            {
                request.getSession().setAttribute("message", "Σφάλμα κατά την αλλαγή των στοιχείων της ταινίας!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
            }
        }
    }
}
