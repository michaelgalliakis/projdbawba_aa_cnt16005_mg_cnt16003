<%@page import="dbawba.data.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dbawba.data.Movie"%>
<%@page import="dbawba.tools.Globals"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

    <% 
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                      
                    
                     String sqlWhereCondition = "" ;
                     String sqlWhereConditionFromParameter = request.getParameter("sqlWhereCondition") ;
                    if (sqlWhereConditionFromParameter!=null)                    
                      sqlWhereCondition = sqlWhereConditionFromParameter;
                             
                    
                    int rows = 10 ;    
                         String strRowsByUser = request.getParameter("numberOfRows") ;
                    if (strRowsByUser!=null && Globals.isNumeric(strRowsByUser))
                    {
                        int rowsByUser =Integer.parseInt(strRowsByUser) ;
                        if (rowsByUser>0)
                            rows = rowsByUser ;
                    }         
                    
                    int category = 0 ;    
                    String strCategory = request.getParameter("categories") ;
                    if (strCategory!=null && Globals.isNumeric(strCategory))                    
                        category =Integer.parseInt(strCategory) ;                        
                    
                       String search = "" ;
                        String searchPar = request.getParameter("search");                                      
                        if (searchPar!=null)
                            search = searchPar;
                        
                        String type = "A" ;
                        String typePar = request.getParameter("type");              
                        if (typePar!=null && (typePar.equals("T") || typePar.equals("D")))
                            type = typePar ;
                    
                    
                    
                            ArrayList<Movie> movies = new ArrayList<>();     
                            Integer numberOfAllRows = Movie.numberOfAllRowsFromselectMoviesFromDB(sqlWhereCondition);
                            int allPages = ((numberOfAllRows-1)/rows)+1;
                            
                                 String strCurPage = request.getParameter("curPage") ;
                                  int curPage = 0;
                    if (strCurPage!=null && Globals.isNumeric(strCurPage))
                    {
                        curPage =Integer.parseInt(strCurPage) ;
                        if (curPage<0 || curPage>=allPages)
                            curPage = 0;
                    }                                                                               
                            movies = Movie.selectMoviesFromDB(rows,sqlWhereCondition,curPage);                                                       
    %>                   
    <br/>
    <center>  
        <font color="blue" size="-1"><%=message%> </font>
            <div align="right">                        
            <form id="refreshNumberOfPages" method="post" action="<%=request.getRequestURI()%>">
                Rows:
                <select name="numberOfRows" size="0" style = 'position: relative' onchange="changeNumberOfPages()">                        
                    <option <%=(rows==10)?"selected":""%> value="5">5</option>    
                    <option <%=(rows==10)?"selected":""%> value="10">10</option>                            
                    <option <%=(rows==20)?"selected":""%> value="20">20</option>
                    <option <%=(rows==30)?"selected":""%> value="30">30</option>
                    <option <%=(rows==40)?"selected":""%> value="40">40</option>
                    <option <%=(rows==50)?"selected":""%> value="50">50</option>
                </select>&nbsp;&nbsp;
                <input type="hidden" name="categories" value="<%=category%>">
                <input type="hidden" name="type" value="<%=type%>"> 
                <input type="hidden" name="search" value="<%=search%>"> 
                        
                <script>
                    function changeNumberOfPages(){
                        document.getElementById("refreshNumberOfPages").submit();
                    }
                </script>
            </form>                                    
        </div>
        <hr/>                        
        <font size="3">
        <table>  
            <thead>                              
                <tr>
                    <th>Play </th>
                    <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Title&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>  
                    <th>Director</th>  
                    <th>category</th>                                  
                    <th>Reviews</th>                                                                                                 
                    <th>By user</th>  
                </tr>  
            </thead>                          
           <%
                for (int i = 0 ; i<movies.size();i++)
                            {                            
            %>
            <tr>                              
                <td><a href="playMovie.jsp?id=<%=movies.get(i).getId()%>">
                        <button>
                            <input type="image" src="Images/myImages/play.png" alt="Play" height="24" width="48">
                        </button>
                    </a>
                </td>                               
                <td><%=movies.get(i).getTitle()%></td>                                           
                <td><%=movies.get(i).getDirector()%></td>                                              
                <td><%=movies.get(i).getCategoryName()%></td>                                                                                                            
                <td><%=movies.get(i).getTotalcommnets() %></td>                  
            <%  
                User user = (User)session.getAttribute("User") ;
                if (user!=null && user.getUserID()==movies.get(i).getUserID()) { %>
                <td>
                    Me!<a href="editMovie.jsp?id=<%=movies.get(i).getId()%>">(<img src="Images/myImages/edit.png" width="12" height="12">)                                   
                    </a>
                </td>   
             <% } else { %>
             <td><%=movies.get(i).getUserUsername() %></td>   
             <% } %>
            </tr>                   
            <%}%>
        </table> 
        </font>
        <hr>
        <% if (curPage<allPages-1) { %>
            
        <form action="<%=request.getRequestURI()%>" method="post">   
            <input type="image" src="Images/myImages/front.png" alt="Next" width="96" height="48">
            <input type="hidden" name="numberOfRows" value="<%=rows%>" >
            <input type="hidden" name="curPage" value="<%=curPage+1%>" >
            <input type="hidden" name="categories" value="<%=category%>">
            <input type="hidden" name="type" value="<%=type%>"> 
            <input type="hidden" name="search" value="<%=search%>">                             
        </form>                                                              
        <% } if (curPage>0) { %>                
        <form action="<%=request.getRequestURI()%>" method="post">                  
            <input type="image" src="Images/myImages/back.png" alt="Previous" width="96" height="48">    
            <input type="hidden" name="curPage" value="<%=curPage-1%>" >
            <input type="hidden" name="numberOfRows" value="<%=rows%>" >
            <input type="hidden" name="categories" value="<%=category%>"> 
            <input type="hidden" name="type" value="<%=type%>"> 
            <input type="hidden" name="search" value="<%=search%>"> 
        </form>                      
            <% } %>            
            All pages: [<%=curPage+1%>/<%=allPages%>]
            <br><br>
    </center> 
                
                