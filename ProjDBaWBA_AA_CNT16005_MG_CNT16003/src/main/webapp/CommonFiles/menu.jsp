<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.data.User"%>
<div id="header">
    <div id="logo"></div>
    <div id="<% 
           User user = (User) session.getAttribute("User") ;
            if (user!=null) {
                String type = user.getType() ;
                if (type.equals("V"))
                    out.print("contactVIP") ;
                else if (type.equals("A"))
                    out.print("contactSuper") ;
                else 
                    out.print("contactUser") ;                
            }
            else 
                out.print("contact") ;                
         %>">                                                  
        <center>            
        <%                              
            if (user==null) {
        %>
        <a href="login.jsp" title="">
            <img src="Images/myImages/Login-icon.png" alt ="Login" height="36" width="36"/>
        </a>
        <a href="signUp.jsp" title="">
            <img src="Images/myImages/signUp.png" alt ="Sign up" height="36" width="36"/>
        </a>
        <% } else {
            out.println("<span class='btext'><b><u>"+user.getUsername()+"</u></b></span>");
        %>                               
        <a href="profile.jsp" title="">
            <img src="Images/myImages/profile.png" alt ="Sign up" height="36" width="36"/>
        </a>
        <a href="logout.jsp" title="">
            <img src="Images/myImages/Logout-icon.png" alt ="Login" height="36" width="36"/>
        </a>
        <%
           }
        %>                                   
        </center>            
    </div>                        
    <div class="corner">             
        <div id="smashnav">
            <ul>
                    <% String filename = request.getRequestURI() ; %>
                    <li><a href="index.jsp" <% if (filename.equals("/ProjDBaWBA/index.jsp")|| filename.equals("/ProjDBaWBA/pronunciation.jsp")) out.println("class='current'"); %> title=""><span>HOME</span></a></li>
                    <li><a href="recentMovies.jsp" <% if (filename.equals("/ProjDBaWBA/recentMovies.jsp")) out.println("class='current'"); %> title=""><span>Recent Movies</span></a></li>
                    <li><a href="categories.jsp" <% if (filename.equals("/ProjDBaWBA/categories.jsp")) out.println("class='current'"); %> title=""><span>Categories</span></a></li>
                    <li><a href="search.jsp" <% if (filename.equals("/ProjDBaWBA/search.jsp")) out.println("class='current'"); %> title=""><span>Search</span></a></li>
                    
                    <% if (user!=null) { %>
                    <li><a href="newMovie.jsp" <% if (filename.equals("/ProjDBaWBA/newMovie.jsp")) out.println("class='current'"); %> title=""><span>New Movie</span></a></li>
                    <% } else { %>
                    <li><label><span>New Movie</span></label></li>
                    <% } %>                                        
                    
                    <% if (user!=null && user.getType().equals("A")) { %>
                    <li><a href="administration.jsp"  <% if (filename.equals("/ProjDBaWBA/administration.jsp") || filename.equals("/ProjDBaWBA/manageCategories.jsp")
                            || filename.equals("/ProjDBaWBA/UpdateUser") || filename.equals("/ProjDBaWBA/editUser.jsp")
                            || filename.equals("/ProjDBaWBA/manageUsers.jsp")) out.println("class='current'"); %> title=""><span>Administration</span></a></li>
                    <% } else { %>
                    <li><label><span>Administration</span></label></li>
                    <% } %>                   
            </ul>
        </div>        
    </div>
</div>