package dbawba.data;

import dbawba.tools.Debug;
import dbawba.tools.ManageDBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Comment {
    private int userID ;
    private int movieID;
    private int review;
    private java.util.Date cDate ;
    private String comment ;
    private String userUsername ;
    
    public Comment() {
    }
    
    public boolean loadCommentFromDB(int locUserID, int locMovieID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID, movieID, review, comment, cDate " +
                "FROM comments WHERE userID = "+ locUserID +" AND movieID = "+locMovieID+ ";" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                userID = resultTable.getInt("userID");
                movieID = resultTable.getInt("movieID");
                review = resultTable.getInt("review");
                comment = resultTable.getString("comment");
                Timestamp timestamp = resultTable.getTimestamp("cDate");
                if (timestamp != null)
                    cDate = new java.util.Date(timestamp.getTime());
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean insertNewCommentToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String insert = "INSERT INTO comments " +
                "(userID," +
                "movieID," +
                "review," +
                "comment," +
                "cDate) " +
                "VALUES\n" +
                "('"+userID+"'," +
                "'"+movieID+"'," +
                "'"+review+"'," +
                "'"+comment+"'," +
                "'"+new java.sql.Timestamp(cDate.getTime())+"');" ;
        
        boolean result = mdbc.executeUpdate(insert, true);
        mdbc.closeDBConnection();
        
        return result ;
    }
    
    public boolean updateCommentToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String update = "UPDATE comments SET " +
                "review = " + review + "" +
                "comment = '" + comment + "'" +
                " WHERE userID = " + userID + " AND movieID = "+movieID+" ;";
        
        boolean result = mdbc.executeUpdate(update, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public boolean deleteCommentFromDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String delete ="DELETE FROM comments WHERE userID = " + userID + " AND movieID = "+movieID+" ;";
        boolean result = mdbc.executeUpdate(delete, true);
        
        mdbc.closeDBConnection();
        
        return result;
    }
    
    //Static methods:
    public static boolean delAnyoneCommentFromDB(int locUserID,int locMovieID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        
        String delete ="DELETE FROM comments WHERE userID = " + locUserID + " AND movieID = "+locMovieID+" ;";
        boolean result =mdbc.executeUpdate(delete, true);
        
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public static ArrayList<Comment> allCommentsOfAFilmFromDB(int mID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID, movieID, review, comment, cDate, " +
                "(SELECT username FROM users WHERE users.userID = comments.userID) AS userUsername " +
                "FROM comments WHERE movieID = "+mID+" ORDER BY cDate DESC ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        ArrayList<Comment> resultWithComments=new ArrayList<>();
        
        try {
            while(resultTable.next()) {
                Comment newComment = new Comment();
                newComment.setUserID(resultTable.getInt("userID"));
                newComment.setMovieID(resultTable.getInt("movieID"));
                newComment.setReview(resultTable.getInt("review"));
                newComment.setComment(resultTable.getString("comment"));
                newComment.setUserUsername(resultTable.getString("userUsername"));
                Timestamp timestamp = resultTable.getTimestamp("cDate");
                if (timestamp != null)
                    newComment.setcDate(new java.util.Date(timestamp.getTime()));
                
                resultWithComments.add(newComment) ;
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return null ;
        }
        
        mdbc.closeDBConnection();
        return resultWithComments;
    }
    
    public void setAll(int userID, int movieID, int review, String comment,java.util.Date cDate) {
        this.userID = userID;
        this.movieID = movieID;
        this.review = review;
        this.comment = comment;
        this.cDate = cDate ;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getMovieID() {
        return movieID;
    }
    
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
    
    public int getReview() {
        return review;
    }
    
    public void setReview(int review) {
        this.review = review;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Date getcDate() {
        return cDate;
    }
    
    public void setcDate(Date cDate) {
        this.cDate = cDate;
    }
    
    public String getUserUsername() {
        return userUsername;
    }
    
    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
