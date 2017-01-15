package dbawba.tools;

import dbawba.data.Comment;
import dbawba.data.User;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Globals {
    public static DateFormat df = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
    
    public static int getAverageRate(ArrayList<Comment> arComment)
    {
        if (arComment.isEmpty())
            return 0 ;
        float sum = 0 ;
        for (Comment comment : arComment)
            sum += comment.getReview() ;
        return (int) Math.ceil(sum/arComment.size()) ;
    }
    public static String fixSQLString(String url)
    {
        return url.replaceAll("'", "`");
    }
    
    public static String fixYoutubeURL(String url)
    {
        return url.replaceAll("www.youtube.com/watch\\?v=", "www.youtube.com/embed/");
    }
    public static User checkUser(HttpServletRequest request, HttpServletResponse response)
    {
        return checkUser(request, response, false);
    }
    public static User checkUser(HttpServletRequest request, HttpServletResponse response, boolean onlyAdmin)
    {
        User user = (User) request.getSession().getAttribute("User") ;
        if (user==null)
            user = tryLoginWithCookies(request) ;
        
        if (user==null)
        {
            request.getSession().setAttribute("message", "Δεν έχετε τα κατάλληλα δικαιώματα πρόσβασης!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            try {
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                Debug.printException(ex);
            }
        }
        else if (onlyAdmin && !user.getType().equals("A"))
        {
            request.getSession().setAttribute("message", "Δεν έχετε δικαιώματα διαχείρισης!");
            RequestDispatcher rd = request.getRequestDispatcher("AuxiliaryFiles/error.jsp");
            try {
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                Debug.printException(ex);
            }
        }
        
        return user ;
    }
    
    public static User tryLoginWithCookies(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        String username = "" ;
        String password = "" ;
        
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("username")) {
                    username = cookies[i].getValue() ;
                }
                else if (cookies[i].getName().equals("password")) {
                    password  = cookies[i].getValue() ;
                }
            }
        }
        Debug.println(username);
        Debug.println(password);
        if (!username.equals("") && !password.equals(""))
        {
            if (!User.isThereTheUser(username,password))
            {
                return null ;
            }
            else
            {
                User newUser = new User();
                newUser.loadUserFromDB(username) ;
                request.getSession().setAttribute("User", newUser);
                
                return newUser ;
            }
        }
        else
            return null ;
    }
    
    
    /**
     * Είναι μια συνάρτηση που ελέγχει αν μια συμβολοσειρά είναι αριθμός .
     * @param sNum Μια συμβολοσειρά για έλεγχο .
     * @return True αν είναι αριθμός η συμβολοσειρά ή False αν δεν είναι .
     */
    public static boolean isNumeric(String sNum)
    {
        double res ;
        try {
            res = Double.parseDouble(sNum);
        }
        catch (Exception ex)
        {
            //Nothing
            return false ;
        }
        return true ;
    }
    
    /**
     * Συνάρτηση που επιστρέφει το μήνυμα που του δίνουμε στην παράμετρο
     * με κρυπτογραφημένη μορφή σύμφωνα με τον αλγόριθμο MD5 .
     * Στην περίπτωση μας χρησιμοποιείται γιατί θέλουμε να αποθηκεύονται οι
     * κωδικοί των λογαριασμών και χρηστών κρυπτογραφημένοι στην βάση .
     * Και έτσι αν για κάποιο λόγο διαρρεύσουν να μην μπορούν κάποιοι κακόβουλοι χρήστες
     * να αποκτήσουν πρόσβαση.
     * @param mess Το αρχικό μας μήνυμα .
     * @return Το κρυπτογραφημένο μήνυμα .
     */
    public static String getHashCode_MD5_Algorithm(String mess)
    {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(mess.getBytes());
            byte[] messageDigestMD5 = messageDigest.digest();
            StringBuilder stringBuffer = new StringBuilder();
            for (byte bytes : messageDigestMD5)
                stringBuffer.append(String.format("%02x", bytes & 0xff));
            return stringBuffer.toString() ;
        } catch (NoSuchAlgorithmException exception) {
            return "" ;
        }
    }
}
