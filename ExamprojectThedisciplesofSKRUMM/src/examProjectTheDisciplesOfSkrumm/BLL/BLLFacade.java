/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL;


import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.UserManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class BLLFacade implements BLLFacadeInterface
{
    private TreeTableUtilInterface treeTableUtil;
    private TaskManagerInterface taskmanager;
    private SecurityManagerInterface securityManager;
    private UserManagerInterface userManager;

    public BLLFacade() throws IOException 
    {
        treeTableUtil = new TreeTableUtil();
        taskmanager = new TaskManager();
        securityManager = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
        userManager = new UserManager();
    }
    

   

    @Override
    public TreeItem<Task> getModel(ObservableList<Task> tasks) {
        return treeTableUtil.getModel(tasks);
    }

   

    @Override
    public String hashPassword(String password) throws SecurityException 
    {
        return securityManager.hashPassword(password);
    }

    @Override
    public Boolean checkUser(User user) throws SQLException
    {
        return userManager.checkUser(user);
    }

    @Override
    public void passwordHash(User user) throws SQLException
    {
        userManager.passwordHash(user);
    }

    @Override
    public User getUser(User user) throws SQLException
    {
        return userManager.getUser(user);
    }

    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException {
        return userManager.getAllUsers();
    }

    @Override
    public boolean userExist(User user) throws SQLException {
        return userManager.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException {
        return userManager.createUser(user);
    }

    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        taskmanager.newInterval(interval);
    }

    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
       return taskmanager.getSixTasks(user);
    }

}
