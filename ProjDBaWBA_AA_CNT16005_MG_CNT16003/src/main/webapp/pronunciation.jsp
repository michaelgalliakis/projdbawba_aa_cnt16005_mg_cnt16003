<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    Globals.tryLoginWithCookies(request) ;        
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
                <p>Εκφώνηση της άσκησης<a href="OtherFiles/Master_Assignment_1_2016_17.pdf"> (πατήστε εδώ για download)</a>:</p>
                <hr/>
                <object data="OtherFiles/Master_Assignment_1_2016_17.pdf" type="application/pdf" width="80%" height="650px">                    
                </object>    
                <form action="index.jsp">                        
                    <input type="image" src="Images/myImages/back.png" alt="Previous" width="96" height="48">
                </form>   
            </div>           
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>
