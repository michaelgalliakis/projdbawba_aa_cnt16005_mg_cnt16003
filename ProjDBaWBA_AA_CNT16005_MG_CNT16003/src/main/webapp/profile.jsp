<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    User user = Globals.checkUser(request,response);
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
    <%                    
        String message = "";
        if (session.getAttribute("message") != null) {
            message = (String) session.getAttribute("message");
            session.removeAttribute("message");
        }
    %>                                                            
    <div id="container">
        <jsp:include page="CommonFiles/menu.jsp"/>           
        <div id="middle">
            <div id="middle_inside">
                <center>                        
                    <font color="green"><%=message%> </font><br>                                            
                    <table>                                                      
                        <tr>
                            <th>Username: </th> <td><b><%=user.getUsername() %></b></td>                                
                        </tr>  
                        <tr>
                            <th>Name: </th> <td><b><%=user.getName() %></b></td>                                
                        </tr>  
                        <tr>
                            <th>Surname: </th> <td><b><%=user.getSurname() %></b></td>                                
                        </tr>  
                        <tr>
                            <th>Email: </th> <td><b><%=user.getEmail() %></b></td>                                
                        </tr>  
                        <tr>
                            <th>Total movies: </th> <td><b><%=user.getTotalmovies()%></b></td>                                
                        </tr>                          
                    </table>                            
                </center>                                          
                <a href="editProfile.jsp"> <input type="button" class="buttonEditProfile" name="Edit"> </a><br>   
                <a href="viewMyMovies.jsp"> <input type="button" class="buttonViewMyMovies" name="View"> </a><br>                                                       
            </div>                    
        </div>                
    </div>
    <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>