/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;

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
public class Usermanager implements UserManagerInterface
{
    private final DALFacade dal;
    private final SecurityManagerInterface sm;
    
    public Usermanager() throws IOException
    {
        dal = new DALFacade();
        sm = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
    }
    
    public Boolean checkUser(User user) throws SQLException
    {
        User CheckedUser = dal.getUser(user);
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
    
    public void hashPassword(User user) throws SQLException
    {
        
        String pass = sm.hashPassword(user.getPassword());
        User hashedUser = new User(user.getEmail(), user.getFirstName(), user.getLastName(), pass, user.getIsAdmin());
        dal.updateUserPassword(hashedUser);
    }
    
    public static void main(String[] args) throws SQLException, IOException
    {
        Usermanager um = new Usermanager();
        User test = new User("standard@user.now", "Mads", "Jensesn", "nemt", false);
        um.hashPassword(test);
        System.out.println(um.dal.getUser(test));
        
    }
    
}
