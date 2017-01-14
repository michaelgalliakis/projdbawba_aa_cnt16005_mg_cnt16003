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
                    <font color="blue" size="-1"><%=message%> </font>
                    <hr/>
                    <% 
                            ArrayList<User> users = new ArrayList<>();                            
                            users = User.selectUsersFromDB("") ;                            
                    %>
                    <table>  
                        <thead>                              
                            <tr>                        
                                <th>Username</th>  
                                <th>Name</th>  
                                <th>Surname</th>  
                                <th>Type</th>                                  
                                <th>Total movies</th>  
                                <th>Edit User</th>  
                                <th>Delete User</th>  
                            </tr>  
                        </thead>                          
                        <%
                            for (int i = 0 ; i<users.size();i++)
                            {                            
                            %>
                        <tr>                              
                            <td><%=users.get(i).getUsername()%></td>  
                            <td><%=users.get(i).getName()%></td>                                           
                            <td><%=users.get(i).getSurname()%></td>                  
                            <td><img height="24" width="24" src="<%                                                                 
                if (users.get(i).getType().equals("V"))
                    out.print("Images/myImages/vip.png") ;
                else if (users.get(i).getType().equals("A"))
                    out.print("Images/myImages/system.png") ;
                else 
                    out.print("Images/myImages/user.png") ;                            
                                %>"</td>  
                            <td><%=users.get(i).getTotalmovies()%></td> 
                            <td><a href="editUser.jsp?username=<%=users.get(i).getUsername()%>">
                                    <button>
                                        <input type="image" src="Images/myImages/editDevice.png" alt="Delete" height="36" width="36">
                                    </button>
                                </a>
                            </td>      
                            <td><a href="DeleteUser?id=<%=users.get(i).getUserID()%>">
                                    <button  onClick="return deleteMessage('τον χρήστη');" >
                                        <input type="image" src="Images/myImages/RemoveUser.png" alt="Delete" height="36" width="36">
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