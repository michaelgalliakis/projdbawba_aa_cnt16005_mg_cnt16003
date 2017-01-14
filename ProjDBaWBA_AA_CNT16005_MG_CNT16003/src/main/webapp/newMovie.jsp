<%@page import="dbawba.data.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    Globals.checkUser(request,response);
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>           
            <div id="middle">
                <br/>         
                <%
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>                
                <h3>Καταχώρηση Νέας ταινίας:</h3>
                <font color="red" size="-1"><%=message%> </font>
                <br/>
                <form action="NewMovie" method="post" name="newmovie">                    
                    <hr/>  
                    <label for="Url">Δώσε Url:</label><br />                    
                    <input type="text" id="textinput" name="Url" placeholder="Url" class="textinput" maxlength="150" value="" autofocus/><br />
                    <label for="Title">Δώσε το όνομα της ταινίας:</label><br />
                    <input type="Title" id="passwordinput" name="Title" placeholder="Title" class="textinput" maxlength="45" value="" /><br />
                    <label for="Director">Δώσε το όνομα του σκηνοθέτη:</label><br />
                    <input type="Director" id="passwordinput" name="Director" placeholder="Director" class="textinput" maxlength="45" value="" /><br />
                    Επέλεξε κατηγορία <br>
                    <select name="categories" size="0">
                    <%
                        ArrayList<Category> categ = new ArrayList<>() ;
                        categ = Category.selectAllCategoriesFromDB() ;
                        
                        String tmp ;
                        for(Category cat : categ)
                        {
                            tmp = "<option value='"+ cat.getId()+"'>"+cat.getName()+"</option>" ;
                            out.println(tmp);
                        }
                    %>
                    </select>
                    <br><br>                  
                    <input type="submit" value="" class="createCategory" name="newmovie">                   
                </form>
                <hr/>                                
            </div>              
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>
