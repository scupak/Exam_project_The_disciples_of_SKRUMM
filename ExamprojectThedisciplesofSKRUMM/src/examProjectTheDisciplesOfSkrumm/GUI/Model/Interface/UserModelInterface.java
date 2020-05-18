/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author SKRUMM
 */
public interface UserModelInterface
{
    /**
     * checks the user
     * @param user
     * @return true if successful, false otherwise
     * @throws SQLException 
     */ 
    public boolean checkUser(User user) throws SQLException;
     
     /**
      * hashes the given string
      * @param password
      * @return hashed string
      */
     public String hashPassword(String password);
     
     /**
      * gets a user
      * @param user
      * @return a user
      * @throws SQLException 
      */
     public User getUser(User user) throws SQLException;
    
     /**
      * gets all users
      * @return all users
      * @throws SQLServerException
      * @throws SQLException 
      */
     public ObservableList<User> getAllUsers() throws SQLServerException, SQLException;
    
     /**
      * checks if the user exists
      * @param user
      * @return true if successful, false otherwise
      * @throws SQLException 
      */
     public boolean userExist(User user) throws SQLException;
    
    /**
     * creates a user
     * @param user
     * @return the new user
     * @throws SQLException 
     */
     public User createUser(User user) throws SQLException;
    
    /**
     * gets the current user
     * @return the current user
     */
    public User getCurrentuser();
    
    /**
     * sets the current user
     * @param currentuser 
     */
    public void setCurrentuser(User currentuser);
    
    /**
     * updates a user
     * @param oldUser
     * @param newUser
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException;
    
    /**
     * deletes a user
     * @param user
     * @return true if successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteUser(User user) throws SQLException;
    
    /**
     * gets all the users projects
     * @param user
     * @return all the users projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException;
    
    /**
     * adds a user to a project
     * @param user
     * @param project
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException;
    
    /**
     * deletes a project from a user
     * @param user
     * @param project
     * @return true if successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException;
     
    /**
     * sets the current user mode
     * @param userMode 
     */
    public void setCurrentUserMode(UserMode userMode);
    
    /**
     * gets the current usermode
     * @return the current usermode
     */
    public UserMode getCurrentUserMode();
    
    /**
     * sets the current admin
     * @param currentAdmin 
     */
    public void setCurrentAdmin(User currentAdmin);
    
    /**
     * gets the current admin
     * @return the current admin
     */
    public User getCurrentAdmin();
    
    /**
     * gets the 100 most recent logs
     * @return the 100 most recent logs
     * @throws SQLServerException
     * @throws SQLException 
     */
    public ObservableList<String> getAllLogs() throws SQLServerException, SQLException;
}
