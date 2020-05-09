/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.UserModelInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lumby
 */
public class UserModel implements UserModelInterface
{
     private final BLLFacadeInterface bllFacade;
     private ObservableList<User> users;
     private User currentuser;
     
     public UserModel() throws IOException, SQLException
     {
         bllFacade = new BLLFacade();
         this.users = FXCollections.observableArrayList();
         users.addAll(bllFacade.getAllUsers());
     }
     
     public boolean checkUser(User user) throws SQLException
     {
        user.setIsAdmin(bllFacade.getUser(user).getIsAdmin());
         return bllFacade.checkUser(user);
     }
     
     public String hashPassword(String password)
     {
         return bllFacade.hashPassword(password);
     }
     
     

    @Override
    public User getUser(User user) throws SQLException {
        return bllFacade.getUser(user);
    }

    @Override
    public ObservableList<User> getAllUsers() throws SQLServerException, SQLException {
        users.clear();
        users.addAll(bllFacade.getAllUsers());
        return users;
    }

    @Override
    public boolean userExist(User user) throws SQLException {
        return bllFacade.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException {
        return bllFacade.createUser(user);
    }
    @Override
    public User getCurrentuser() {
        return currentuser;
    }
    @Override
    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return bllFacade.updateUser(oldUser, newUser);
    }
    
    
}
