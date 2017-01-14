<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    session.setAttribute("User", null);
%>
<html>
    <head>
        <jsp:include page="CommonFiles/header.jsp"/>               
    </head>
    <body>
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>  
            <div id="middle">
                <div id="middle_inside">
                    <div id="slide">
                        
                    </div>                    
                <%                    
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>                    
                <div id="quick_note">
                    <center><h3><u>Σύνδεση :</u></h3></center>
                    <font color="red" size="-1"><%=message%> </font>
                    <form role="form" method="post" action="Login">  
                        <label for="textinput">Username:</label> <br>
                        <input id="textinput" name="username"  class="textinput" maxlength="25" type="text" autofocus> <br>    
                        <label for="passwordinput">Password:</label><br>
                        <input id="passwordinput" name="pass" class="textinput" maxlength="25" type="password" value="">    <br>
                        <label for="rememberMeInput">Να με θυμάσαι:</label>
                        <input id="rememberMeInput" name="rememberMe" type="checkbox" value="YES">    <br/>
                        <center>                                
                            <input type="submit" class="loginNow" value="" name="login" >                                                                   
                            <a href="signUp.jsp"> <input type="button" class="signupNow" value="" name="signup"> </a><br>   
                            <hr/>
                            <img src="Images/myImages/master96.png" width="96" height="96"  align = "center"/>
                            <hr/>
                        </center>
                    </form>  
                </div>
                </div>
            </div>    
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>