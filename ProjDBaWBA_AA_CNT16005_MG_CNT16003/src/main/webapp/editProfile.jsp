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
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>           
            <div id="middle">
                <br>
                <form action="UpdateUser" method="post" onSubmit="return registationformValidation();" name="registration">                    
                    <label for="Username">Όνομα χρήστη:</label>
                    <%=user.getUsername() %><br />
                    <label for="Password1">Κωδικός:</label><br />
                    <input type="password" id="passwordinput" name="Password1" placeholder="Password" class="textinput" maxlength="25" value="*$@#*&HGF**" /><br />
                    <label for="Password2">Επιβεβαίωση κωδικού:</label><br />
                    <input type="password" id="passwordinput" name="Password2" placeholder="Confirm Password" class="textinput" maxlength="25" value="*$@#*&HGF**" /><br />
                    <label for="Name">Όνομα:</label><br />
                    <input type="text" id="textinput" name="Name" placeholder="Name" class="textinput" maxlength="25" value="<%=user.getName() %>"/><br />
                    <label for="Surname">Επίθετο:</label><br />
                    <input type="text" id="textinput" name="Surname" placeholder="Surname" class="textinput" maxlength="25" value="<%=user.getSurname() %>"/><br />            
                    <label for="Email">Email:</label><br />
                    <input type="text" id="textinput" name="Email" placeholder="Email" class="textinput" maxlength="25" value="<%=user.getEmail() %>"/><br />      
                    <br />          
                    <input type="submit" value="" name="registration" class="changeUser" />                                        
                    <div id="stylesheetTest"></div>
                </form>
                <br>
                <form action="DeleteUser" method="post" onSubmit="return deleteMessage('το λογαριασμό σου');">                                                               
                    <input type="submit" name="deleteUser"  class="deleteUser" value="" />                                        
                </form>                      
                <form action="profile.jsp">                        
                    <input type="image" src="Images/myImages/back.png" alt="Previous" width="96" height="48">
                </form>    
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>