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

    /**
     * Gets a users first name
     *
     * @return String firstName
     */
    public String getFirstName()
    {
        return firstName.get();
    }

    /**
     * Set a users first name
     *
     * @param firstName
     */
    public void setFirstName(String firstName)
    {
        this.firstName = new SimpleStringProperty(firstName);
    }

    /**
     * Gets the last name for a user
     *
     * @return string lastName
     */
    public String getLastName()
    {
        return lastName.get();
    }

    /**
     * Sets the last name for a user
     *
     * @param lastName
     */
    public void setLastName(String lastName)
    {
        this.lastName = new SimpleStringProperty(lastName);
    }

    /**
     * Gets the email for a user
     *
     * @return string email
     */
    public String getEmail()
    {
        return email.get();
    }

    /**
     * Sets the email for a user
     *
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = new SimpleStringProperty(email);
    }

    /**
     * Gets the isAdmin boolean for a user
     *
     * @return boolean isAdmin
     */
    public Boolean getIsAdmin()
    {
        return isAdmin;
    }

    /**
     * Sets the isAdmin boolean for a user
     *
     * @param isAdmin
     */
    public void setIsAdmin(Boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    /**
     * Gets the password for a user
     *
     * @return string password
     */
    public String getPassword()
    {
        return password.get();
    }

    /**
     * sets the password for a user
     *
     * @param password
     */
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
