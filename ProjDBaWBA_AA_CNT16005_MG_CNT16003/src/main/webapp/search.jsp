<%@page import="java.util.ArrayList"%>
<%@page import="dbawba.data.Category"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
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
                <div align="left">
                    <%
                        String search = "" ;
                        String searchPar = request.getParameter("search");                                      
                        if (searchPar!=null)
                            search = searchPar;
                        
                        String type = "A" ;
                        String typePar = request.getParameter("type");              
                        if (typePar!=null && (typePar.equals("T") || typePar.equals("D")))
                            type = typePar ;
                                 
                        String sqlWhereCondition = "" ;
                        if (!search.equals(""))
                        {                            
                            String[] arrWords =search.split("\\s+") ;                            
                        switch(type){
                            case "A":
                                sqlWhereCondition = " WHERE (Director like LOWER('%" + Globals.fixSQLString(arrWords[0]).toLowerCase() +"%') "+
                                                    " OR Title like LOWER('%" +Globals.fixSQLString(arrWords[0]).toLowerCase()+"%')) " ;
                                for (int i=1;i<arrWords.length;i++)                                
                                    sqlWhereCondition += " OR (Director like LOWER('%" + Globals.fixSQLString(arrWords[i]).toLowerCase() +"%') "+
                                                    " OR Title like LOWER('%" +Globals.fixSQLString(arrWords[i]).toLowerCase()+"%')) " ;                                
                                break ;
                            case "T":
                                sqlWhereCondition = " WHERE Title like LOWER('%" + Globals.fixSQLString(arrWords[0]).toLowerCase() +"%') " ;
                                for (int i=1;i<arrWords.length;i++)                                
                                    sqlWhereCondition += " OR Title like LOWER('%" + Globals.fixSQLString(arrWords[i]).toLowerCase() +"%') " ;                                
                                break ;
                            case "D":
                                sqlWhereCondition = " WHERE Director like LOWER('%" + Globals.fixSQLString(arrWords[0]).toLowerCase() +"%') " ;
                                for (int i=1;i<arrWords.length;i++)                                
                                    sqlWhereCondition += " OR Director like LOWER('%" + Globals.fixSQLString(arrWords[i]).toLowerCase() +"%') " ;                                                                                                
                                break ;                                                            
                        }                        
                        }                        
                    %>
                    <br>
                    <form action="search.jsp" method="post">                          
                        <label>&nbsp;&nbsp;&nbsp;Search: </label>
                        <input type="text" id="textinput" name="search" placeholder="Αναζήτηση" class="textinput" maxlength="40" value="<%=search%>" autofocus/>
                        <input type="submit" class="search" value="">     
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        Only Title:<input type="radio" <%=type.equals("T")?"checked":""%> name="type" value="T"> |
                        All:<input type="radio" <%=type.equals("A")?"checked":""%> name="type" value="A"> |
                        Only Director:<input type="radio"  <%=type.equals("D")?"checked":""%> name="type" value="D">               
                    </form>     
                </div>                                                                 
                <jsp:include page="CommonFiles/viewTableWithMovies.jsp">    
                    <jsp:param name="sqlWhereCondition" value="<%=sqlWhereCondition%>" />                        
                    <jsp:param name="search" value="<%=search%>" />   
                    <jsp:param name="type" value="<%=type%>" />   
                </jsp:include>                                                  
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>