/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.UserManagerInterface;
import examProjectTheDisciplesOfSkrumm.DAL.DALFacade;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author lumby
 */
public class UserManager implements UserManagerInterface
{
    private final DALFacade dal;
    private final SecurityManagerInterface sm;
    
    public UserManager() throws IOException
    {
        dal = new DALFacade();
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
    
    public static void main(String[] args) throws SQLException, IOException
    {
        UserManager um = new UserManager();
        User test = new User("standard@user.now", "Mads", "Jensesn", "nemt", false);
        System.out.println(um.dal.getUser(test));
        
    }

    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException {
        return dal.getAllUsers();
    }

    @Override
    public boolean userExist(User user) throws SQLException {
        return dal.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException {
        return dal.createUser(user);
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return dal.updateUser(oldUser, newUser);
    }
    
}
