<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.setAttribute("User", null);
    //Remove cookies
    Cookie killUsernameCookie = new Cookie("username", null);
    killUsernameCookie.setMaxAge(0);     
    response.addCookie(killUsernameCookie);
    Cookie killPasswordCookie = new Cookie("password", null);
    killPasswordCookie.setMaxAge(0);    
    response.addCookie(killPasswordCookie);     
    
    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
    rd.forward(request, response);    
%>
