/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL.Interface;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public interface UserDBDAOInterface
{
    /**
     * gets all users as a list
     * @return all users as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<User> getAllUsers() throws SQLServerException, SQLException;
    
    /**
     * checks if the user exists
     * @param user
     * @return true if it exists, false otherwise
     * @throws SQLException 
     */
    public boolean userExist(User user) throws SQLException;
    
    /**
     * creates a user
     * @param user
     * @return a user
     * @throws SQLException 
     */
    public User createUser(User user) throws SQLException;
    
    /**
     * gets a user
     * @param user
     * @return the user
     * @throws SQLException 
     */
    public User getUser(User user) throws SQLException;
    
    /**
     * updates the users password
     * @param user
     * @return true if it is updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException;
    
    /**
     * updates the given info for the user
     * @param oldUser
     * @param newUser
     * @return true if it is updated, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException;
    
    /**
     * deletes a user
     * @param user
     * @return true if it is deleted, false otherwise
     * @throws SQLException 
     */
    public boolean deleteUser(User user) throws SQLException;
    
    /**
     * gets a list of all the projects the user is assigned to
     * @param user
     * @return a list of all the projects the user is assigned to
     * @throws SQLServerException
     * @throws SQLException 
     */
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException;
    
    /**
     * adds a user to the given project
     * @param user
     * @param project
     * @return true if it is added to the project, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException;
    
    /**
     * deletes a user from the given project
     * @param user
     * @param project
     * @return true if it is deleted from the user, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException;
}
