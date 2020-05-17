/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.UserManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacadeFactory;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lumby
 */
public class UserManager implements UserManagerInterface
{
    private final DALFacadeInterface dal;
    private final SecurityManagerInterface sm;
    
    public UserManager(DALFacadeInterface dal) throws IOException, Exception
    {
        this.dal = dal;
        sm = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
    }
    
    /**
     * checcks the a given users email and password,
     * to see if they match with a user in the database
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public Boolean checkUser(User user) throws SQLException
    {
        User CheckedUser = getUser(user);
        if(CheckedUser == null)
        {
            return false;
        }
        if(CheckedUser.getEmail().equals(user.getEmail()))
        {
            if(CheckedUser.getPassword().equals(user.getPassword()))
            {
                System.out.println("true");
                return true;
            }
        }
        System.out.println("false");
        return false;
    }
    
    /**
     * Gets a specific user
     * @param user
     * @return user
     * @throws SQLException 
     */
    @Override
    public User getUser(User user) throws SQLException
    {
        return dal.getUser(user);
    }
    
    /**
     * hashes a users password
     * @param user
     * @throws SQLException 
     */
    @Override
    public void passwordHash(User user) throws SQLException
    {
        
        String pass = sm.hashPassword(user.getPassword());
        User hashedUser = new User(user.getEmail(), user.getFirstName(), user.getLastName(), pass, user.getIsAdmin());
        dal.updateUserPassword(hashedUser);
    }
    
    public static void main(String[] args) throws SQLException, IOException, Exception
    {
        UserManager um = new UserManager(DALFacadeFactory.CreateDALFacade(DALFacadeFactory.DALFacadeTypes.PRODUCTION));
        User test = new User("standard@user.now", "Mads", "Jensesn", "nemt", false);
        System.out.println(um.dal.getUser(test));
        
    }

    /**
     * Gets all the users
     * @return List users
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException {
        return dal.getAllUsers();
    }

    /**
     * checks if a user exists
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean userExist(User user) throws SQLException {
        return dal.userExist(user);
    }

    /**
     * creates a new user
     * @param user
     * @return user
     * @throws SQLException 
     */
    @Override
    public User createUser(User user) throws SQLException {
        return dal.createUser(user);
    }

    /**
     * updates an existing user
     * @param oldUser
     * @param newUser
     * @return user
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return dal.updateUser(oldUser, newUser);
    }

    /**
     * deletes a user
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteUser(User user) throws SQLException {
        return dal.deleteUser(user);
    }

    /**
     * get all user for a project
     * @param user
     * @return List usersForProject
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
         return  dal.getAllUserProjects(user);
    }

    /**
     * adds a user to a project
     * @param user
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
      return  dal.addUserToProject(user, project);
    }

    /**
     * deletes a project from a user
     * @param user
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
         return dal.deleteProjectFromUser(user, project);
    }

    @Override
    public List<String> getAllLogs() throws SQLServerException, SQLException {
       return dal.getAllLogs();
    }
    
}
