package dbawba.tools;

import java.sql.SQLException;

public class Debug {
    
    public static void println(String message)
    {
        System.out.println(message) ;
    }
    
    public static void printException(Exception ex)
    {
        if (ex instanceof SQLException)
            System.out.println(((SQLException)ex).getMessage()) ;
        if (ex instanceof ClassNotFoundException)
            System.out.println(((ClassNotFoundException)ex).getMessage()) ;
        if (ex instanceof InstantiationException)
            System.out.println(((InstantiationException)ex).getMessage()) ;
        if (ex instanceof IllegalAccessException)
            System.out.println(((IllegalAccessException)ex).getMessage()) ;
        else
            System.out.println(ex.getMessage()) ;
    }
}
