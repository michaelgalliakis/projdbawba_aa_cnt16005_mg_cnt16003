package dbawba.controllers;

import dbawba.data.Comment;
import dbawba.data.User;
import dbawba.tools.Globals;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/NewComment")
public class NewComment extends HttpServlet {
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
        //Received parameters
        String rate = request.getParameter("rate");
        String strMovieID = request.getParameter("movieID");
        String comment = request.getParameter("Comment");
        Comment newComment = new Comment();
        if (strMovieID!=null && Globals.isNumeric(strMovieID))
            newComment.setMovieID(Integer.parseInt(strMovieID));
        else
        {
            request.getSession().setAttribute("message", "Σφάλμα κατά την δημιουργία σχόλιου!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
        
        newComment.setComment(Globals.fixSQLString(comment));
        newComment.setUserID(user.getUserID());
        
        newComment.setcDate(new java.util.Date());
        if (rate!=null && Globals.isNumeric(rate))
            newComment.setReview(Integer.parseInt(rate));
        else
            newComment.setReview(3);
        
        if (newComment.insertNewCommentToDB())
        {
            request.getSession().setAttribute("message", "Προστέθηκε το σχόλιο!");
            RequestDispatcher rd = request.getRequestDispatcher("playMovie.jsp?id="+newComment.getMovieID());
            rd.forward(request, response);
        }
        else
        {
            request.getSession().setAttribute("message", "Σφάλμα κατά την δημιουργία σχόλιου!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
    }
}
