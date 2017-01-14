<%@page import="dbawba.data.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    Globals.checkUser(request,response,true);
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>           
            <div id="middle">
                 <%
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>                                
                <br/>
                <center>
                    <div align="left">
                        <form action="NewCategory" method="post">                          
                            <label>&nbsp;&nbsp;&nbsp;Δώσε όνομα νέας κατηγορίας</label>
                            <input type="text" id="textinput" name="Category" placeholder="Νέα κατηγορία" class="textinput" maxlength="25" value="" autofocus/>
                            <input type="submit" class="newDeviceButton" value="">    
                            <font color="blue"><%=message%> </font>
                        </form>     
                    </div>
                    <hr/>
                    <% 
                            ArrayList<Category> categories = new ArrayList<>();                            
                            categories = Category.selectAllCategoriesFromDB() ;                            
                    %>
                    <table>  
                        <thead>                              
                            <tr>                        
                                <th>Name</th>  
                                <th>movies:</th>   
                                <th>Delete Category</th>  
                            </tr>  
                        </thead>                          
                        <%
                            for (int i = 0 ; i<categories.size();i++)
                            {                            
                            %>
                            <tr>                              
                                <td><%=categories.get(i).getName()%></td>  
                                <td><%=categories.get(i).getMovies()%></td>                                           
                                <td><a href="DeleteCategory?id=<%=categories.get(i).getId()%>">
                                        <button  onClick="return deleteMessage('την κατηγορία');" >
                                            <input type="image" src="Images/myImages/release.png" alt="Delete" height="36" width="36">
                                        </button>
                                    </a>
                                </td>                     
                            </tr>                   
                        <%}%>
                    </table>                      
                    <form action="administration.jsp">                        
                        <input type="image" src="Images/myImages/back.png" alt="Previous" width="96" height="48">
                    </form>    
                </center>                 
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>