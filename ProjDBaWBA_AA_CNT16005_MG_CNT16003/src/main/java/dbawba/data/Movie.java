package dbawba.data;

import dbawba.tools.Debug;
import dbawba.tools.ManageDBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Movie {
    private int id ;
    private String url ;
    private String title;
    private String director;
    private String photo ;
    private int userID ;
    private String userUsername ;
    private int Category ;
    private String CategoryName ;
    private int totalcommnets;
    private java.util.Date cDate ;
    
    public Movie() {
    }
    
    public boolean loadMovieFromDB(int mID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT m.id, m.url, m.title, m.director, " +
                "m.photo, m.userID, m.Category, m.totalcomments, m.cDate, " +
                "u.username AS userUsername, " +
                "c.name AS CategoryName " +                
                "FROM movies m "+
                "INNER JOIN users u ON u.userID = m.userID " +
                "INNER JOIN categories c ON c.id = m.Category " +
                "WHERE m.id = "+ mID +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                id = resultTable.getInt("id");
                url = resultTable.getString("url");
                title = resultTable.getString("title");
                director = resultTable.getString("director");
                photo = resultTable.getString("photo");
                userID = resultTable.getInt("userID");
                Category = resultTable.getInt("Category");
                totalcommnets = resultTable.getInt("totalcomments");
                Timestamp timestamp = resultTable.getTimestamp("cDate");
                if (timestamp != null)
                    cDate = new java.util.Date(timestamp.getTime());
                if (resultTable.getString("CategoryName")!=null)
                    CategoryName = resultTable.getString("CategoryName");
                else
                    CategoryName = "NoCategory";
                userUsername = resultTable.getString("userUsername");
                
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean insertNewMovieToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String insert = "INSERT INTO movies " +
                "(url," +
                "title," +
                "director," +
                "photo," +
                "userID," +
                "cDate," +
                "Category) " +
                "VALUES\n" +
                "('"+url+"'," +
                "'"+title+"'," +
                "'"+director+"'," +
                "'"+photo+"'," +
                ""+userID+"," +
                "'"+new java.sql.Timestamp(cDate.getTime())+"'," +
                ""+Category+");" ;
        
        boolean result = mdbc.executeUpdate(insert, true);
        mdbc.closeDBConnection();
        
        return result ;
    }
    
    public boolean updateMovieToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String update = "UPDATE movies SET " +
                "title = '" + title + "'," +
                " director = '" + director + "'," +
                " photo = '" + photo + "'," +
                " Category = '" + Category + "'" +
                " WHERE id = " + id + " ;";
        
        boolean result = mdbc.executeUpdate(update, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public boolean deleteMovieFromDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String delete ="DELETE FROM comments WHERE movieID = " + id +" ;";
        
        mdbc.executeUpdate(delete, true);
        
        delete ="DELETE FROM movies WHERE id = " + id +" ;";
        
        boolean result = mdbc.executeUpdate(delete, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    //Static methods:
    public static boolean delAnyoneMovieFromDB(int mID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String delete ="DELETE FROM comments WHERE movieID = " + mID +" ;";
        
        mdbc.executeUpdate(delete, true);
        
        delete ="DELETE FROM movies WHERE id = " + mID +" ;";
        
        boolean result =mdbc.executeUpdate(delete, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public static boolean isThereTheURL(String mUrl)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id FROM movies WHERE url = '"+ mUrl +"' ;" ;
        ResultSet result = mdbc.executeQuery(select, true);
        
        boolean isEmpty = false ;
        try {
            isEmpty = result.next() ;
        } catch (SQLException ex) {
            Debug.printException(ex);
        }
        mdbc.closeDBConnection();
        return isEmpty ;
    }
    
    public static boolean isThereTheID(int mID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id FROM movies WHERE id = '"+ mID +"' ;" ;
        ResultSet result = mdbc.executeQuery(select, true);
        
        boolean isEmpty = false ;
        try {
            isEmpty = result.next() ;
        } catch (SQLException ex) {
            Debug.printException(ex);
        }
        mdbc.closeDBConnection();
        return isEmpty ;
    }
    
    public static ArrayList<Movie> selectMoviesFromDB(int limit,String sqlWhereCondition,int page)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id, url, title, director, " +
                "photo, userID, Category, totalcomments, cDate, " +
                "(SELECT username FROM users WHERE users.userID = movies.userID) AS userUsername, " +
                "(SELECT name FROM categories WHERE categories.id = movies.Category) AS CategoryName " +
                "FROM movies "+ sqlWhereCondition +" " +
                " ORDER BY cDate DESC LIMIT "+ limit +" OFFSET "+(page*limit)+" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        ArrayList<Movie> resultWithUsers=new ArrayList<>();
        
        try {
            while(resultTable.next()) {
                Movie newMovie = new Movie();
                
                newMovie.setId(resultTable.getInt("id"));
                newMovie.setUrl(resultTable.getString("url"));
                newMovie.setTitle(resultTable.getString("title"));
                newMovie.setDirector(resultTable.getString("director"));
                newMovie.setPhoto(resultTable.getString("photo"));
                newMovie.setUserID(resultTable.getInt("userID"));
                newMovie.setCategory(resultTable.getInt("Category"));
                newMovie.setTotalcommnets(resultTable.getInt("totalcomments"));
                Timestamp timestamp = resultTable.getTimestamp("cDate");
                if (timestamp != null)
                    newMovie.setcDate(new java.util.Date(timestamp.getTime()));
                if (resultTable.getString("CategoryName")!=null)
                    newMovie.setCategoryName(resultTable.getString("CategoryName"));
                else
                    newMovie.setCategoryName("NoCategory");
                
                newMovie.setUserUsername(resultTable.getString("userUsername"));
                
                resultWithUsers.add(newMovie) ;
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return null ;
        }
        
        mdbc.closeDBConnection();
        return resultWithUsers;
    }
    
    public static Integer numberOfAllRowsFromselectMoviesFromDB(String sqlWhereCondition)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT count(*) AS numberOfAllRows" +
                " FROM movies "+ sqlWhereCondition +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        Integer numberOfAllRows = 0;
        try {
            if(resultTable.next())
                numberOfAllRows = resultTable.getInt("numberOfAllRows");
            
            
        } catch (SQLException ex) {
            Debug.printException(ex);
            numberOfAllRows= -1 ;
        }
        mdbc.closeDBConnection();
        return numberOfAllRows ;
    }
    
    public void setAll(int id, String url, String title, String director, String photo, int userID, int Category, int totalcommnets, Date cDate)
    {
        this.id = id;
        this.url = url;
        this.title = title;
        this.director = director;
        this.photo = photo;
        this.userID = userID;
        this.Category = Category;
        this.totalcommnets = totalcommnets;
        this.cDate = cDate ;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public int getCategory() {
        return Category;
    }
    
    public void setCategory(int Category) {
        this.Category = Category;
    }
    
    public int getTotalcommnets() {
        return totalcommnets;
    }
    
    public void setTotalcommnets(int totalcommnets) {
        this.totalcommnets = totalcommnets;
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
    
    public String getCategoryName() {
        return CategoryName;
    }
    
    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
}
