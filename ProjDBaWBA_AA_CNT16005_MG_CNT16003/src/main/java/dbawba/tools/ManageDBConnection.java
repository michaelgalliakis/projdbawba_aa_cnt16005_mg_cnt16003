package dbawba.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManageDBConnection {
    public final static String URL = "jdbc:mysql://localhost:3306/";
    public final static String DBNAME = "moviesdb";
    public final static String DRIVER = "com.mysql.jdbc.Driver";
    public final static String USERNAME = "admin";
    public final static String PASSWORD = "12345";
    
    private Connection conn ;
    private java.sql.Statement st = null ;
    private boolean connectionIsOK = false ;
    
    public ManageDBConnection() {
        load();
    }
    
    public void load()
    {
        try
        {
            Class.forName(DRIVER).newInstance();
            conn = DriverManager.getConnection(URL+DBNAME,USERNAME,PASSWORD);
            st = conn.createStatement();
            connectionIsOK = true ;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e)
        {
            Debug.printException(e);
            connectionIsOK = false ;
        }
    }
    
    public boolean executeUpdate(String execText)
    {
        return executeUpdate(execText,false);
    }
    public boolean executeUpdate(String execText, Boolean debug)
    {
        try {
            if (debug)
                System.out.println("Debug: executeUpdate: "+execText);
            
            st.executeUpdate(execText) ;
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        return true ;
    }
    
    public ResultSet executeQuery(String execText)
    {
        return executeQuery(execText,false);
    }
    public ResultSet executeQuery(String execText, Boolean debug)
    {
        try {
            if (debug)
                System.out.println("Debug: executeQuery: "+execText);
            
            return st.executeQuery(execText) ;
        } catch (SQLException ex) {
            Debug.printException(ex);
            return null ;
        }
    }
    public void closeDBConnection()
    {
        try {
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Debug.printException(ex);
        }
    }
    public Connection getConn() {
        return conn;
    }
    
    public Statement getSt() {
        return st;
    }
    
    public boolean isConnectionOK() {
        return connectionIsOK;
    }
}
