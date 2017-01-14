<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dbawba.tools.Globals"%>
<%@page import="dbawba.data.User"%>
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
                <div id="middle_inside">
                    <div id="slide">      
                       Τεχνολογικο Εκπαιδευτικο Ιδρυμα Αθηνας (Τμήμα πληροφορικής) <br> 
                       Π.Μ.Σ στις τεχνολογίες υπολογισμού και δικτύων.<br/>
                   Φοιτητές: <b>Αγγελική Αλεξοπούλου * * * Μιχαήλ Γαλλιάκης</b><br>
                         <a href="pronunciation.jsp">Εργασία για το μάθημα</a> : <br>
                "Βάσεις Δεδομένων και Web-based Εφαρμογές". <br/>
                     <b>Θέμα εργασίας:</b><br> Δημιουργία ιστοσελίδας, μέσω της οποίας θα μπορούν δυνητικοί χρήστες 
                    να προσθέτουν και να βλέπουν ταινίες από το youtube, όπως επίσης και να καταχωρούν σχόλια και βαθμολογίες.
                    <i>Σκοπός είναι να δημιουργηθεί μια κοινότητα, που θα παρέχει την δυνατότητα στα μέλη του, 
                    να μοιράζονται απόψεις και links των  αγαπημένων ταινιών τους.</i>     <br>                    
                    Όλα τα αρχεία βρίσκονται στο <a href="https://Aaggeliki@bitbucket.org/Aaggeliki/projdbawba_aa_cnt16005_mg_cnt16003.git">Bitbucket</a>.
                        <hr>
                         
                Χρήση γλωσσών : HTML, CSS, Javascript, JSP <br/>
                Πλατφόρμα ανάπτυξης: Netbeans (IDE 8.2)<br/>
                Web container: Apache Tomcat 8.0.27.0<br/>
                DBMS: MySQL<br/>
                Άλλα εργαλεία: GIT<br/>                        
                    </div>      
                    <div align="left">
                        <img src="Images/myImages/masterBig.png" width="256" height="256"  align = "center"/>
                    </div>                    
                </div>                
            </div>            
        </div>
        <jsp:include page="CommonFiles/footer.jsp"/>
    </body>
</html>
