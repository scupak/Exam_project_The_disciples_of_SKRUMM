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
 * @author lumby
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
    
    @Override
    public boolean deleteUser(User user) throws SQLException {
       return bllFacade.deleteUser(user);
    }

    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return bllFacade.getAllUserProjects(user);
    }

    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
        return bllFacade.addUserToProject(user, project);
    }

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

    public User getCurrentAdmin()
    {
        return currentAdmin;
    }

    public void setCurrentAdmin(User currentAdmin)
    {
        this.currentAdmin = currentAdmin;
    }
    
    
}
