/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.UserModelInterface;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author SKRUMM
 */
public class UserModel implements UserModelInterface
{
     private final BLLFacadeInterface bllFacade;
     private ObservableList<User> users;
     private User currentuser;
     private User currentAdmin;
     private UserMode userMode; 
     
     
     public UserModel() throws IOException, SQLException, Exception
     {
         bllFacade = new BLLFacade();
         this.users = FXCollections.observableArrayList();
         users.addAll(bllFacade.getAllUsers());
     }
     
     /**
      * checks the current user if the user is and admin, and if it can log in
      * @param user
      * @return true if successful, false otherwise
      * @throws SQLException 
      */
     public boolean checkUser(User user) throws SQLException
     {
        user.setIsAdmin(bllFacade.getUser(user).getIsAdmin());
        return bllFacade.checkUser(user);
     }
     
     /**
      * Hashes passwords
      * @param password
      * @return hashed password
      */
     public String hashPassword(String password)
     {
         return bllFacade.hashPassword(password);
     }
     
     /**
      * gets a user
      * @param user
      * @return the user
      * @throws SQLException 
      */
    @Override
    public User getUser(User user) throws SQLException {
        return bllFacade.getUser(user);
    }

    /**
     * gets all users
     * @return all users as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLServerException, SQLException {
        users.clear();
        users.addAll(bllFacade.getAllUsers());
        return users;
    }

    /**
     * checks if the user exists
     * @param user
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean userExist(User user) throws SQLException {
        return bllFacade.userExist(user);
    }

    /**
     * creates a user
     * @param user
     * @return the new user
     * @throws SQLException 
     */
    @Override
    public User createUser(User user) throws SQLException {
        return bllFacade.createUser(user);
    }
    
    /**
     * gets the current user
     * @return the current user
     */
    @Override
    public User getCurrentuser() {
        return currentuser;
    }
    
    /**
     * sets the current user
     * @param currentuser 
     */
    @Override
    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }

    /**
     * updates the user
     * @param oldUser
     * @param newUser
     * @return true if operation successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return bllFacade.updateUser(oldUser, newUser);
    }
    
    /**
     * deletes a user
     * @param user
     * @return true if operation successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteUser(User user) throws SQLException {
       return bllFacade.deleteUser(user);
    }

    /**
     * gets all the projects for the user
     * @param user
     * @return all the projects as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return bllFacade.getAllUserProjects(user);
    }

    /**
     * adds a user to a project
     * @param user
     * @param project
     * @return true if operation successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
        return bllFacade.addUserToProject(user, project);
    }

    /**
     * deletes a project from a user
     * @param user
     * @param project
     * @return true if operation successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
       return  bllFacade.deleteProjectFromUser(user, project);
    }
    
    /**
     * Sets the parameter that tells the program if its a teacher or student
     * using the program.
     *
     * @param userMode
     */
    public void setCurrentUserMode(UserMode userMode)
    {
        this.userMode = userMode;
    }
    
     /**
     * Get the currentuserMode
     *
     * @return
     */
    public UserMode getCurrentUserMode()
    {
        return userMode;
    }

    /**
     * gets the current admin
     * @return the current admin
     */
    public User getCurrentAdmin()
    {
        return currentAdmin;
    }

    /**
     * sets the current admin
     * @param currentAdmin 
     */
    public void setCurrentAdmin(User currentAdmin)
    {
        this.currentAdmin = currentAdmin;
    }

    /**
     * gets the 100 most recent log entries
     * @return the 100 most recent log entries
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public ObservableList<String> getAllLogs() throws SQLServerException, SQLException {
        
        ObservableList<String>  allLogs =  FXCollections.observableArrayList();
        allLogs.addAll(bllFacade.getAllLogs());
       
        
        return  allLogs;
    }
    
    
}
