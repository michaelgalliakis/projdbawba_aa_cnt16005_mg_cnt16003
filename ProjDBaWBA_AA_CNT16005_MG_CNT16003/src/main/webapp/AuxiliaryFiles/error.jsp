<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>    
        <jsp:include page="../CommonFiles/header.jsp"/>            
    </head>
    <body >
        <div id="container">
            <jsp:include page="../CommonFiles/menu.jsp"/>           
            <div id="middle">
                <div id="middle_inside">
                    <div id="slide">
        <%
		String message = "";
		if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
		}
	%>
        <h1>Error </h1>
        <h2><%=message%></h2>                         
                    </div>      
                    <div align="left">                        
                    </div>                    
                </div>                
            </div>            
        </div>
        <jsp:include page="../CommonFiles/footer.jsp"/>
    </body>
</html>
