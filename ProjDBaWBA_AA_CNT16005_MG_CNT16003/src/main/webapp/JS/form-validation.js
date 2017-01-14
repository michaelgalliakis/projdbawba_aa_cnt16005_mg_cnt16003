function changePath(username,AccessType)
{
    
    var lastHREF = document.getElementById("Button"+username).href ;
    lastHREF = lastHREF.substring(0, lastHREF.length - 1);
    document.getElementById("Button"+username).href = lastHREF + AccessType ;    
}
function registationformValidation()  
{  
    var uid = document.registration.UsernameR;  
    var pass1 = document.registration.Password1;  
    var pass2 = document.registration.Password2;  
    var uname = document.registration.Name;  
    var usurname = document.registration.Surname;  
    var uemail = document.registration.Email;  
    
    if(userid_validation(uid,5,12))  
    
    {  
        if(passid_validation(pass1,pass2,5,12))  
        {  
            if(allLetter(uname))  
            {
                if(allLetter(usurname))  
                {  
                    if(ValidateEmail(uemail))  
                    {  
                        return true;
                    }   
                }  
            }   
        }  
    }  
    
    return false;  
}  
function deleteMessage(mess)  
{  
    var r = confirm("Είσαι σίγουρος ότι θέλεις να διαγράψεις "+mess+";");
    if (r) {
        return true ;
    } else {
        return false ;
    } 
}
function changeProgileformValidation()  
{      
    var pass1 = document.Profile.Password1;  
    var pass2 = document.Profile.Password2;  
    var uname = document.Profile.Name;  
    var usurname = document.Profile.Surname;  
    var uemail = document.Profile.Email;  
        
    if(passid_validation(pass1,pass2,5,12))  
    {  
        if(allLetter(uname))  
        {
            if(allLetter(usurname))  
            {  
                if(ValidateEmail(uemail))  
                {  
                    return true;
                }   
            }  
        }   
    }  
        
    return false;  
}  
function userid_validation(uid,mx,my)  
{  
    var uid_len = uid.value.length;  
    if (uid_len == 0 || uid_len >= my || uid_len < mx)  
    {  
        alert("User Id should not be empty / length be between "+mx+" to "+my);  
        uid.focus();  
        return false;  
    }  
    return true;  
} 
function passid_validation(pass1,pass2,mx,my)  
{  
    var passid_len = pass1.value.length;  
    if (passid_len === 0 ||passid_len >= my || passid_len < mx)  
    {  
        alert("Password should not be empty / length be between "+mx+" to "+my);  
        pass1.focus();  
        return false;  
    }  
    passid_len = pass2.value.length;  
    if (passid_len === 0 ||passid_len >= my || passid_len < mx)  
    {  
        alert("Password should not be empty / length be between "+mx+" to "+my);  
        pass2.focus();  
        return false;  
    }  
    if (pass2.value!==pass1.value)  
    {
        alert("Τα Password δεν είναι ίδια");  
        pass1.focus();  
        return false;  
    }        
    else
        return true;  
} 
function allLetter(uname)  
{   
    var letters = /^[A-Za-z]+$/;  
    if(uname.value.match(letters))  
    {  
        return true;  
    }  
    else  
    {  
        alert('Must have alphabet characters only');  
        uname.focus();  
        return false;  
    }  
} 
function alphanumeric(uadd)  
{   
    var letters = /^[0-9a-zA-Z]+$/;  
    if(uadd.value.match(letters))  
    {  
        return true;  
    }  
    else  
    {  
        alert('User address must have alphanumeric characters only');  
        uadd.focus();  
        return false;  
    }  
} 
function ValidateEmail(uemail)  
{  
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
    if(uemail.value.match(mailformat))  
    {  
        return true;  
    }  
    else  
    {  
        alert("You have entered an invalid email address!");  
        uemail.focus();  
        return false;  
    }  
} 