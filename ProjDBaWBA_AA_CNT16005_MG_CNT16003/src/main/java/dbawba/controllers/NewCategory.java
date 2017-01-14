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

@WebServlet("/NewCategory")
public class NewCategory extends HttpServlet {
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
        
        Globals.checkUser(request,response,true);
        //Received parameters
        String category = request.getParameter("Category");
        
        boolean isThereError = false ;
        if (category.trim().equals(""))
        {
            request.getSession().setAttribute("message", "Υπάρχει κενό στο πεδίο για το category!");
            isThereError = true ;
        }
        else if (Category.isThereTheCategory(category))
        {
            request.getSession().setAttribute("message", "Το category υπάρχει ήδη! Αλλάξτε το.");
            isThereError = true ;
        }
        
        if (isThereError)
        {
            RequestDispatcher rd = request.getRequestDispatcher("manageCategories.jsp");
            rd.forward(request, response);
        }
        else
        {
            Category newCategory = new Category();
            newCategory.setName(Globals.fixSQLString(category));
            if (newCategory.insertNewCategoryToDB())
            {
                request.getSession().setAttribute("message", "Δημιουργήθηκε επιτυχώς η νέα κατηγορία!");
                RequestDispatcher rd = request.getRequestDispatcher("manageCategories.jsp");
                rd.forward(request, response);
            }
            else
            {
                request.getSession().setAttribute("message", "Σφάλμα κατά την δημιουργία νέας κατηγορίας!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
            }
        }
    }
}
