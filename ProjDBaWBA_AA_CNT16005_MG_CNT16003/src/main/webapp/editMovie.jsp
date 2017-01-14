<%@page import="dbawba.data.Movie"%>
<%@page import="dbawba.data.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    User user = Globals.checkUser(request,response);
    
    String mID = request.getParameter("id") ;    
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
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
          <%                    
                    String message = "";
                    if (session.getAttribute("message") != null)
                    {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>  
                <div id="container">
                    <jsp:include page="CommonFiles/menu.jsp"/>           
                    <div id="middle"><br>
                        <font color="green"><%=message%> </font><br>
                     
                        <form action="UpdateMovie" method="post" name="editmovie">                    
                            <hr/>  
                            <label for="Username">Url:</label>
                            <%=editableMovie.getUrl()%><br />                    
                            <label for="Title">Δώσε το όνομα της ταινίας:</label><br />
                            <input type="Title" id="passwordinput" name="Title" placeholder="Title" class="textinput" maxlength="45" value="<%=editableMovie.getTitle()%>" /><br />
                            <label for="Director">Δώσε το όνομα του σκηνοθέτη:</label><br />
                            <input type="Director" id="passwordinput" name="Director" placeholder="Director" class="textinput" maxlength="45" value="<%=editableMovie.getDirector()%>" /><br />
                            Επέλεξε κατηγορία <br>
                            <select name="categories" size="0">
                        <%
                        ArrayList<Category> categ = new ArrayList<>() ;
                        categ = Category.selectAllCategoriesFromDB() ;
                        
                        String tmp ;
                        for(Category cat : categ)
                        {
                            tmp = "<option "+((editableMovie.getCategory()==cat.getId())?"selected":"")+" value='"+ cat.getId()+"'>"+cat.getName()+"</option>" ;
                            out.println(tmp);
                        }

                        %>
                            </select>
                            <br><br>       
                            <input type="hidden" name="mID" value="<%=editableMovie.getId()%>">
                            <input type="submit" value="" name="newmovie" class="changeMovie">                    
                        </form>
                        <br>
                        <form action="DeleteMovie" method="post" onSubmit="return deleteMessage('την ταινία');">                                                               
                            <input type="hidden" name="id"  value="<%=editableMovie.getId()%>" />                                        
                            <input type="submit" class="deleteMovie" name="deleteMovie"   value="" />                                        
                        </form>  
                        <br>                                                
                        <!-- <input type="button" value="Back" onclick="history.back()" alt="Previous" width="96" height="48">  -->
                    </div>
                </div>
                <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>