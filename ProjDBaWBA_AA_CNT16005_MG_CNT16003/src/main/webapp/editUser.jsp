<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    Globals.checkUser(request,response,true);
    
    String uName = request.getParameter("username") ;
    if (uName==null || !User.isThereTheUser(uName))
    {
         request.getSession().setAttribute("message", "Σφάλμα!!! Κατά την τροποποίηση χρήστη!");
                RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
                rd.forward(request, response);
    }             
    User editableUser = new User() ;
    editableUser.loadUserFromDB(uName);
    request.getSession().setAttribute("EditableUser", editableUser);
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>           
            <div id="middle">
                <br><br>                    
                <form action="UpdateUser" method="post" onSubmit="return registationformValidation();" name="registration">                    
                    <label for="Username">Όνομα χρήστη:</label>
                    <%=editableUser.getUsername() %><br />
                    <label for="Password1">Κωδικός:</label><br />
                    <input type="password" id="passwordinput" name="Password1" placeholder="Password" class="textinput" maxlength="25" value="*$@#*&HGF**" /><br />
                    <label for="Password2">Επιβεβαίωση κωδικού:</label><br />
                    <input type="password" id="passwordinput" name="Password2" placeholder="Confirm Password" class="textinput" maxlength="25" value="*$@#*&HGF**" /><br />
                    <label for="Name">Όνομα:</label><br />
                    <input type="text" id="textinput" name="Name" placeholder="Name" class="textinput" maxlength="25" value="<%=editableUser.getName() %>"/><br />
                    <label for="Surname">Επίθετο:</label><br />
                    <input type="text" id="textinput" name="Surname" placeholder="Surname" class="textinput" maxlength="25" value="<%=editableUser.getSurname() %>"/><br />            
                    <label for="Email">Email:</label><br />
                    <input type="text" id="textinput" name="Email" placeholder="Email" class="textinput" maxlength="25" value="<%=editableUser.getEmail() %>"/><br />                              
                    User:<input type="radio"  <%=editableUser.getType().equals("U")?"checked":""%> name="type" value="U"> * * * 
                    VIP:<input type="radio"  <%=editableUser.getType().equals("V")?"checked":""%> name="type" value="V"> * * *
                    Admin:<input type="radio" <%=editableUser.getType().equals("A")?"checked":""%> name="type" value="A"><br>
                    <br />                        
                    <input type="hidden" name="editableUser"  value="YES" />          
                    <input type="submit" value="" name="registration" class="changeUser" />                                            
                    <div id="stylesheetTest"></div>
                </form>
                <br>
                <form action="DeleteUser" method="get" onSubmit="return deleteMessage('τον χρήστη');">                                                               
                    <input type="hidden" name="id"  value="<%=editableUser.getUserID() %>" />                                        
                    <input type="submit" name="deleteUser"  class="deleteUser" value="" />                                   
                </form>  
                <br>
                <form action="manageUsers.jsp">                        
                    <input type="image" src="Images/myImages/back.png" alt="Previous" width="96" height="48">
                </form>                                
            </div>                
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>