package dbawba.data;

import dbawba.tools.Debug;
import dbawba.tools.ManageDBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {
    private int userID ;
    private String username ;
    private String password ;
    private String name ;
    private String surname ;
    private String type ;
    private String email ;
    private int totalmovies ;
    private boolean isDeleted ;
    
    public User() {
    }
    
    public boolean loadUserFromDB(int uID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID, username, password, name, " +
                "surname, type, email, totalmovies, isDeleted " +
                "FROM users WHERE userID = "+ uID +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                userID = resultTable.getInt("UserId");
                username = resultTable.getString("username");
                password = resultTable.getString("password");
                name = resultTable.getString("name");
                surname = resultTable.getString("surname");
                type = resultTable.getString("type");
                email = resultTable.getString("email");
                totalmovies = resultTable.getInt("totalmovies");
                isDeleted = resultTable.getBoolean("isDeleted");
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean loadUserFromDB(String uName) //Return status
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID, username, password, name, " +
                "surname, type, email, totalmovies, isDeleted " +
                "FROM users WHERE username = '"+ uName +"' ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                userID = resultTable.getInt("UserId");
                username = resultTable.getString("username");
                password = resultTable.getString("password");
                name = resultTable.getString("name");
                surname = resultTable.getString("surname");
                type = resultTable.getString("type");
                email = resultTable.getString("email");
                totalmovies = resultTable.getInt("totalmovies");
                isDeleted = resultTable.getBoolean("isDeleted");
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean insertNewUserToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String insert = "INSERT INTO users " +
                "(username," +
                "password," +
                "name," +
                "surname," +
                "type," +
                "email) " +
                "VALUES\n" +
                "('"+username+"'," +
                "'"+password+"'," +
                "'"+name+"'," +
                "'"+surname+"'," +
                "'U'," +
                "'"+email+"');" ;
        
        boolean result = mdbc.executeUpdate(insert, true);
        mdbc.closeDBConnection();
        
        return result ;
    }
    
    public boolean updateUserToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String update = "UPDATE users SET " +
                "password = '" + password + "'," +
                " name = '" + name + "'," +
                " surname = '" + surname + "'," +
                " type = '" + type + "'," +
                " email = '" + email + "'" +
                " WHERE userID = " + userID + " ;";
        
        boolean result = mdbc.executeUpdate(update, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public boolean deleteUserFromDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String delete ="UPDATE users SET isDeleted = 1 WHERE userID = " + userID +" ;";
        
        boolean result = mdbc.executeUpdate(delete, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    //Static methods:
    public static boolean delAnyoneUserFromDB(int uID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String delete = "UPDATE users SET isDeleted = 1 WHERE userID = " + uID +" ;";
        
        boolean result =mdbc.executeUpdate(delete, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public static boolean isThereTheUser(String uName)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT username FROM users WHERE username = '"+ uName +"' ;" ;
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
    
    public static boolean isThereTheUser(String uName,String pWord)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID FROM users WHERE isDeleted = 0 AND username = '"+ uName +"' AND password = '" + pWord +"';" ;
        Debug.println(select);
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
    
    public static ArrayList<User> selectUsersFromDB(String sqlWhereCondition)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT userID, username, password, name, " +
                "surname, type, email, totalmovies, isDeleted " +
                "FROM users WHERE isDeleted = 0 "+ sqlWhereCondition +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        ArrayList<User> resultWithUsers=new ArrayList<>();
        
        try {
            while(resultTable.next()) {
                User newUser = new User();
                
                newUser.setUserID(resultTable.getInt("UserId"));
                newUser.setUsername(resultTable.getString("username"));
                newUser.setPassword(resultTable.getString("password"));
                newUser.setName(resultTable.getString("name"));
                newUser.setSurname(resultTable.getString("surname"));
                newUser.setType(resultTable.getString("type"));
                newUser.setEmail(resultTable.getString("email"));
                newUser.setTotalmovies(resultTable.getInt("totalmovies"));
                newUser.setIsDeleted(resultTable.getBoolean("isDeleted"));
                
                resultWithUsers.add(newUser) ;
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return null ;
        }
        
        mdbc.closeDBConnection();
        return resultWithUsers;
    }
    
    //Set All:
    public void setAll(int userID, String username, String password, String name, String surname, String type, String email, int totalmovies,boolean isDeleted) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.type = type;
        this.email = email;
        this.totalmovies = totalmovies;
        this.isDeleted = isDeleted;
    }
    
    //Getters and Setters:
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getTotalmovies() {
        return totalmovies;
    }
    
    public void setTotalmovies(int totalmovies) {
        this.totalmovies = totalmovies;
    }
    
    public boolean isIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
