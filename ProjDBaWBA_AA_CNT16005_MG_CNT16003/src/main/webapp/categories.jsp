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
                        int category = 0;
                        String categoryStr = request.getParameter("categories");                        
                        if (categoryStr!=null && Globals.isNumeric(categoryStr))
                        {
                         category = Integer.parseInt(categoryStr);
                        }
                    %>
                    <br>
                    <form id="refreshCategories" method="post" action="<%=request.getRequestURI()%>">
                        Επέλεξε κατηγορία:                    
                        <select name="categories" size="0" style = 'position: relative' onchange="change()">    
                            <option <%=((category==0)?"selected":"")%> value="0">--------</option>
                        <%
                        ArrayList<Category> categ = new ArrayList<>() ;
                        categ = Category.selectAllCategoriesFromDB() ;                        
                        String tmp ;
                        for(Category cat : categ)
                        {
                            tmp = "<option "+((category==cat.getId())?"selected":"")+" value='"+ cat.getId()+"'>"+cat.getName()+"("+cat.getMovies()+")"+"</option>" ;
                            out.println(tmp);
                        }
                        %>
                        </select>
                        <script>
                            function change(){
                                document.getElementById("refreshCategories").submit();
                            }
                        </script>
                    </form>
                </div>
                <% if (category!=0) {  String sqlWhereCondition = " WHERE Category = "+category+" " ;%>                        
                <jsp:include page="CommonFiles/viewTableWithMovies.jsp">    
                    <jsp:param name="sqlWhereCondition" value="<%=sqlWhereCondition%>" />
                    <jsp:param name="categories" value="<%=category%>" />
                </jsp:include>                                      
                <% } %>
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>