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

    public void setFirstName(SimpleStringProperty firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(SimpleStringProperty lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(SimpleStringProperty email)
    {
        this.email = email;
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

    public void setPassword(SimpleStringProperty password)
    {
        this.password = password;
    }
   
}
