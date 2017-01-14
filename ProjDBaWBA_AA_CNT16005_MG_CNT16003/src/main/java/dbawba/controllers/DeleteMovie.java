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

@WebServlet("/DeleteMovie")
public class DeleteMovie extends HttpServlet {
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
        
        String mID = request.getParameter("id") ;
        if (mID==null || !Globals.isNumeric(mID) || !Movie.isThereTheID(Integer.parseInt(mID)))
        {
            request.getSession().setAttribute("message", "Σφάλμα!!! Κατά την διαγραφή ταινιας!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
        
        Movie removeMovie = new Movie() ;
        removeMovie.loadMovieFromDB(Integer.parseInt(mID));
        if (removeMovie.getUserID()!=user.getUserID())
        {
            request.getSession().setAttribute("message", "Δεν έχετε τα απαραίτητα δικαιώματα για αυτήν την ταινία!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
        
        if (removeMovie.getUserID()==user.getUserID())
        {
            removeMovie.deleteMovieFromDB() ;
            user.setTotalmovies(user.getTotalmovies()-1);
            request.getSession().setAttribute("message", "Διαγράφηκε επιτυχώς η ταινία!");
            RequestDispatcher rd = request.getRequestDispatcher("viewMyMovies.jsp");
            rd.forward(request, response);
        }
        else
        {
            request.getSession().setAttribute("message", "Σφάλμα!!! Κατά την διαγραφή ταινιας!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
    }
}
