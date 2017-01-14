<%@page import="dbawba.data.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
<!DOCTYPE html>
<%    
     User user = Globals.checkUser(request,response); 
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
                    String sqlWhereCondition = " WHERE userID = "+user.getUserID()+" " ;
                %>
                <br>
                <h2>My movies [<%=user.getTotalmovies()%>]: </h2>                    
                
                <jsp:include page="CommonFiles/viewTableWithMovies.jsp">    
                    <jsp:param name="sqlWhereCondition" value="<%=sqlWhereCondition%>" />                        
                </jsp:include>                                                  
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>