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
                <div id="middle_inside">
                    <a href="manageCategories.jsp">  <input type="button" value="" class="buttonCategories"  name="ManageCategories"> </a><br> <br>  
                    <a href="manageUsers.jsp"> <input type="button" class="buttonUsers" value="" name="ManageUsers"> </a><br>   
                </div>                
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>