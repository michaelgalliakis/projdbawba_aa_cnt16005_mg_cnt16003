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

@WebServlet("/NewMovie")
public class NewMovie extends HttpServlet {
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
        
        User user = Globals.checkUser(request, response) ;
        //Received parameters
        String url = request.getParameter("Url");
        String title = request.getParameter("Title");
        String director = request.getParameter("Director");
        int category =0 ;
        if (request.getParameter("categories")!=null)
            category = Integer.parseInt(request.getParameter("categories"));
        
        boolean isThereError = false ;
        if (url.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για το url!");
            isThereError = true ;
        }
        else if (category==0)
        {
            request.getSession().setAttribute("message", "Δεν έχετε επιλέξει κατηγορία!");
            isThereError = true ;
        }
        else if (title.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για τον τίτλο!");
            isThereError = true ;
        }
        else if (Movie.isThereTheURL(url))
        {
            request.getSession().setAttribute("message", "Το url υπάρχει ήδη! Δώστε κάποιο άλλο!");
            isThereError = true ;
        }
        
        if (isThereError)
        {
            RequestDispatcher rd = request.getRequestDispatcher("newMovie.jsp");
            rd.forward(request, response);
        }
        else
        {
            Movie newMovie = new Movie();
            
            newMovie.setUrl(Globals.fixSQLString(url));
            newMovie.setTitle(Globals.fixSQLString(title));
            newMovie.setDirector(Globals.fixSQLString(director));
            newMovie.setCategory(category);
            newMovie.setUserID(((User)request.getSession().getAttribute("User")).getUserID());
            newMovie.setcDate(new java.util.Date());
            
            if (newMovie.insertNewMovieToDB())
            {
                user.setTotalmovies(user.getTotalmovies()+1);
                request.getSession().setAttribute("message", "Προστέθηκε επιτυχώς η ταινία!");
                RequestDispatcher rd = request.getRequestDispatcher("recentMovies.jsp");
                rd.forward(request, response);
            }
            else
            {
                request.getSession().setAttribute("message", "Σφάλμα κατά την καταχώρηση της ταινίας!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
            }
        }
    }
}
