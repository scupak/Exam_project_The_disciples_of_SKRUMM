/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface UserManagerInterface
{
    /**
     * checks user, by hashing the given password and comparing that along with the username to other users in the database to see if the given user is present
     * @param user
     * @return true or false, depending on wether or not the user is already present.
     * @throws SQLException 
     */
    public Boolean checkUser(User user) throws SQLException;
    
    /**
     * hashes the given password
     * @param user
     * @throws SQLException 
     */
    public void passwordHash(User user) throws SQLException;
    
    /**
     * gets a user
     * @param user
     * @return a user as a user object
     * @throws SQLException 
     */
    public User getUser(User user) throws SQLException;
    
    /**
     * gets a list of all users
     * @return a list of all users
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<User> getAllUsers() throws SQLServerException, SQLException;
    
     /**
      * checks if the user already exists, using the user's email
      * @param user
      * @return true or false, depending on wether or not the user is already present.
      * @throws SQLException 
      */
    public boolean userExist(User user) throws SQLException;
    
    /**
     * creates a user, using the given information
     * @param user
     * @return the newly created user
     * @throws SQLException 
     */
    public User createUser(User user) throws SQLException;
    
    /**
     * updates the user by moving all the retained and unchanged information into a new user, and then adding the new changed information
     * @param oldUser
     * @param newUser
     * @return true, if the user was updated, false if otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException;
    
    /**
     * deletes a user
     * @param user
     * @return true if the user was deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteUser(User user) throws SQLException;
    
    /**
     * get a list of all the projects for the given user
     * @param user
     * @return a list of all the projects for the given user
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException;
    
    /**
     * adds the given user to the given project
     * @param user
     * @param project
     * @return true if the user was added, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException;
    
    /**
     * deletes a project form a user, practically unassigning the user from the project
     * @param user
     * @param project
     * @return true if the user was deleted from the project, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException;
    
    /**
     * gets the 100 most recent entries in the log
     * @return the 100 most recent entries in the log as strings in the log window
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<String> getAllLogs() throws SQLServerException, SQLException;
    
    
    
}
