package dbawba.data;

import dbawba.tools.Debug;
import dbawba.tools.ManageDBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Category {
    private int id ;
    private String name ;
    private int movies ;
    
    public Category() {
    }
    
    public boolean loadCategoryFromDB(int locID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id, name, movies " +
                "FROM categories WHERE id = "+ locID +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                id = resultTable.getInt("id");
                name = resultTable.getString("name");
                movies = resultTable.getInt("movies");
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean loadCategoryFromDB(String locName) //Return status
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        String select = "SELECT id, name, movies " +
                "FROM categories WHERE name = "+ locName +" ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        try {
            if(resultTable.next()) {
                id = resultTable.getInt("id");
                name = resultTable.getString("name");
                movies = resultTable.getInt("movies");
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return false ;
        }
        
        mdbc.closeDBConnection();
        return true;
    }
    
    public boolean insertNewCategoryToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String insert = "INSERT INTO categories " +
                "(name) " +
                " VALUES\n" +
                " ('"+name+"');" ;
        
        
        boolean result = mdbc.executeUpdate(insert, true);
        mdbc.closeDBConnection();
        
        return result ;
    }
    
    public boolean updateCategoryToDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String update = "UPDATE categories SET " +
                "name = '" + name + "'" +
                " WHERE id = " + id + " ;";
        
        boolean result = mdbc.executeUpdate(update, true);
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public boolean deleteCategoryFromDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        String update = "UPDATE movies SET Category = NULL WHERE Category = " + id +" ;";
        boolean result = mdbc.executeUpdate(update, true);
        
        if (result)
        {
            String delete ="DELETE FROM categories WHERE id = " + id +" ;";
            result = mdbc.executeUpdate(delete, true);
        }
        
        mdbc.closeDBConnection();
        
        return result;
    }
    
    //Static methods:
    public static boolean delAnyoneCategoryFromDB(int locID)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String update = "UPDATE movies SET Category = NULL WHERE Category = " + locID +" ;";
        boolean result = mdbc.executeUpdate(update, true);
        
        if (result)
        {
            String delete = "DELETE FROM categories WHERE id = " + locID +" ;";
            result =mdbc.executeUpdate(delete, true);
        }
        
        mdbc.closeDBConnection();
        
        return result;
    }
    
    public static boolean isThereTheCategory(String locName)
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id FROM categories WHERE name = '"+ locName +"' ;" ;
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
    
    public static ArrayList<Category> selectAllCategoriesFromDB()
    {
        ManageDBConnection mdbc = new ManageDBConnection() ;
        
        String select = "SELECT id, name, movies " +
                "FROM categories ;" ;
        
        ResultSet resultTable = mdbc.executeQuery(select, true);
        
        ArrayList<Category> resultWithCategories=new ArrayList<>();
        
        try {
            while(resultTable.next()) {
                Category newCategory = new Category();
                newCategory.setId(resultTable.getInt("id"));
                newCategory.setName(resultTable.getString("name"));
                newCategory.setMovies(resultTable.getInt("movies"));
                
                resultWithCategories.add(newCategory) ;
            }
        } catch (SQLException ex) {
            Debug.printException(ex);
            return null ;
        }
        
        mdbc.closeDBConnection();
        return resultWithCategories;
    }
    
    public void setAll(int id, String name, int movies) {
        this.id = id;
        this.name = name;
        this.movies = movies;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getMovies() {
        return movies;
    }
    
    public void setMovies(int movies) {
        this.movies = movies;
    }
}
