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
    private SimpleStringProperty isAdmin;


    public User(String firstName, String lastName, String email, String isAdmin)
    {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.isAdmin = new SimpleStringProperty(isAdmin);
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public String getEmail()
    {
        return email.get();
    }

    public String getIsAdmin()
    {
        return isAdmin.get();
    }
    
    
    
}
