<%@page import="dbawba.data.Comment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dbawba.data.Movie"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
<!DOCTYPE html>
<%    
    Globals.tryLoginWithCookies(request) ;   
    
    String id = request.getParameter("id") ;
    if (id==null || !Globals.isNumeric(id) || !Movie.isThereTheID(Integer.parseInt(id)))
    {
        request.getSession().setAttribute("message", "Σφάλμα!!! Κατά την αναπαραγωγή ταινίας!");
        RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
        rd.forward(request, response);
    }             
    
    Movie movie = new Movie() ;                  
    movie.loadMovieFromDB(Integer.parseInt(id)) ;           
%>
<html>
    <head>    
        <jsp:include page="CommonFiles/header.jsp"/>            
    </head>
    <body >
        <div id="container">
            <jsp:include page="CommonFiles/menu.jsp"/>           
            <div id="middle">
                 <%                    
                    String message = "";
                    if (session.getAttribute("message") != null) {
			message = (String) session.getAttribute("message");
                        session.removeAttribute("message");
                    }
                %>  
                
                <br><font color="blue"><%=message%> </font><br><br>                
                <div id="quick_note">
                    <i>Title:</i><br><b> <%=movie.getTitle()%></b><hr>
                    <i>Director</i><br><b> <%=movie.getDirector()%></b><hr>
                    <i>Category</i><b> <%=movie.getCategoryName()%></b><hr>
                    <i>By user:</i>
                    <%  
                        User user = (User)session.getAttribute("User") ;
                        if (user!=null && user.getUserID()==movie.getUserID()) { %>                            
                            <b>Me!<a href="editMovie.jsp?id=<%=movie.getId()%>">(<img src="Images/myImages/edit.png" width="12" height="12">)</a></b>                              
                       <% } else { %>
                            <b><%=movie.getUserUsername() %></b>
                       <% } %>
                       <hr>                       
                       <i>Entry date:</i><br><b> <%=Globals.df.format(movie.getcDate())%></b><hr>
                </div>
                <iframe width="490" height="345" src="<%=Globals.fixYoutubeURL(movie.getUrl())%>" allowfullscreen>
                </iframe>                      
                <hr>
                <i>Reviews:</i>[<b><%=movie.getTotalcommnets()%></b>]
                    
                    <%
            ArrayList<Comment> allComments = Comment.allCommentsOfAFilmFromDB(movie.getId()) ;
            int average = Globals.getAverageRate(allComments) ;
            for (int i=0; i<average;i++) out.println("<img src='Images/myImages/star_gold_24.png'>");
            out.println("<hr>");
            boolean userHasWriteReview = false ;
            if (user==null)
                userHasWriteReview = true ;
            
            for (Comment comment : allComments) {
                if (user!=null && comment.getUserID()==user.getUserID())
                    userHasWriteReview = true ;
            %>
            <div align="left">&nbsp;&nbsp;&nbsp;<i>User:</i>
                <%   if (user!=null && user.getUserID()==comment.getUserID()) { %>                            
                <b>Me!<a href="DeleteComment?mID=<%=movie.getId()%>">(<img src="Images/myImages/deleteReview.png" width="12" height="12">)</a></b>                              
                        <% } else { %>
                <b><%=comment.getUserUsername() %></b>
                <% } %>    
            </div>
                    
            <% for (int i=0; i<comment.getReview();i++) out.println("<img src='Images/myImages/star_gold_24.png'>"); %><br>            
            <div id="comment">  
                <p>
                    <%= comment.getComment().replace("\n", "<br/>") %>
                </p>
            </div>
            <div align="right">Date:<%= Globals.df.format(comment.getcDate()) %>&nbsp;&nbsp;&nbsp;</div>
            <hr>          
            <% }
            if (!userHasWriteReview) {
            %>
                        
            <form action="NewComment" method="post" >
                <hr/>                
                <label for="rate">Rate</label>
                <select name="rate" size="0" style = 'position: relative'>                        
                    <option value="1">1</option>    
                    <option value="2">2</option>                            
                    <option selected value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>                
                </select>
                <input type="hidden" name="movieID" value="<%= movie.getId()%>">
                <textarea id="textareainput" name="Comment" placeholder="Add a review"  maxlength="300" rows="4" cols="50" autofocus=""></textarea>                    
                <input type="submit" value="" name="Request" class="addReview" />
                <hr/>
            </form>
                <% } %>
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>
