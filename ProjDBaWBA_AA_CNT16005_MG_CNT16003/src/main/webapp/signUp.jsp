<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    session.setAttribute("User", null);
%>
<html>
    <head>
        <jsp:include page="CommonFiles/header.jsp"/>               
    </head>
    <body onload="document.registration.Username.focus();"> 
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>                
            <div id="middle">
                <br/>         
                <%
                    String username = ((request.getParameter("username")==null)?"":request.getParameter("username"));        
                    String name = ((request.getParameter("name")==null)?"":request.getParameter("name"));   
                    String surname = ((request.getParameter("surname")==null)?"":request.getParameter("surname"));   
                    String email = ((request.getParameter("email")==null)?"":request.getParameter("email"));   
        
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>                
                <h3>Δημιουργία Νέου λογαριασμού χρήστη:</h3>
                <font color="red" size="-1"><%=message%> </font>
                <br/>
                <form action="NewUser" method="post" onSubmit="return registationformValidation();" name="registration">                    
                    <hr/>  
                    <label for="Username">Όνομα χρήστη:</label><br />                    
                    <input type="text" id="textinput" name="UsernameR" placeholder="Username" class="textinput" maxlength="25" value="<%=username%>" autofocus/><br />
                    <label for="Password1">Κωδικός:</label><br />
                    <input type="password" id="passwordinput" name="Password1" placeholder="Password" class="textinput" maxlength="25" value="" /><br />
                    <label for="Password2">Επιβεβαίωση κωδικού:</label><br />
                    <input type="password" id="passwordinput" name="Password2" placeholder="Confirm Password" class="textinput" maxlength="25" value="" /><br />
                    <label for="Name">Όνομα:</label><br />
                    <input type="text" id="textinput" name="Name" placeholder="Name" class="textinput" maxlength="25" value="<%=name%>"/><br />
                    <label for="Surname">Επίθετο:</label><br />
                    <input type="text" id="textinput" name="Surname" placeholder="Surname" class="textinput" maxlength="25" value="<%=surname%>"/><br />            
                    <label for="Email">Email:</label><br />
                    <input type="text" id="textinput" name="Email" placeholder="Email" class="textinput" maxlength="25" value="<%=email%>"/><br />      
                    <br />                    
                    <img src="CaptchaGenerator"> <br>
                    <input type="text" maxlength="5" size="5" name="usercaptcha"><br><br>                    
                    <input type="submit" value="" name="registration" class="createAccount" />                                            
                </form>
                <hr/>
                <center><b>Already registered ?</b> <br></b><a href="login.jsp"><input type="image" src="Images/myImages/login2.png" height="48" width="48"/></a></center>                    
            </div>                
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>
