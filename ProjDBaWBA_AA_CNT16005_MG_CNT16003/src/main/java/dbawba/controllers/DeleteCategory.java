package dbawba.controllers;

import dbawba.data.Category;
import dbawba.tools.Globals;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteCategory")
public class DeleteCategory extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Globals.checkUser(request,response,true);
        
        String id = request.getParameter("id");
        if (id!=null && Globals.isNumeric(id))
        {
            Category.delAnyoneCategoryFromDB(Integer.parseInt(id));
            
            request.getSession().setAttribute("message", "Διαγράφηκε επιτυχώς η κατηγορία!");
            RequestDispatcher rd = request.getRequestDispatcher("manageCategories.jsp");
            rd.forward(request, response);
        }
        else
        {
            request.getSession().setAttribute("message", "Σφάλμα κατά την διαγραφή κατηγορίας!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            rd.forward(request, response);
        }
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
        RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
        rd.forward(request, response);
    }
}
