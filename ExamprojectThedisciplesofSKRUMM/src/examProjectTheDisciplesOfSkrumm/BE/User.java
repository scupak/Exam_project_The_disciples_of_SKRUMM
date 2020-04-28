/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author lumby
 */
public class User
{
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private Boolean isAdmin;


    public User(String email, String firstName, String lastName, String password, boolean isAdmin)
    {
        this.email = new SimpleStringProperty(email);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.password = new SimpleStringProperty(password);
        this.isAdmin = isAdmin;
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String firstName)
    {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String lastName)
    {
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(String email)
    {
        this.email = new SimpleStringProperty(email);
    }

    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public String getPassword()
    {
        return password.get();
    }

    public void setPassword(String password)
    {
        this.password = new SimpleStringProperty(password);
    }

    @Override
    public String toString()
    {
        return "User " + getFirstName() + "{" + "email=" + getEmail() + ", firstName=" + getFirstName() + ", lastName=" + getLastName() + ", password=" + getPassword() + ", isAdmin=" + getIsAdmin() + '}';
    }
    
    
   
}
