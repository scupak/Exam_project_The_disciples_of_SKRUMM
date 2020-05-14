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
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ClientManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ProjectManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.SecurityManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TaskManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.TreeTableUtilInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.UserManagerInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private ProjectManagerInterface projectmanager;
    private ClientManagerInterface clientmanager;

    public BLLFacade() throws IOException 
    {
        projectmanager = new ProjectManager();
        treeTableUtil = new TreeTableUtil();
        taskmanager = new TaskManager();
        securityManager = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
        userManager = new UserManager();
        clientmanager = new ClientManager();
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

    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskmanager.getTasksForUser(user, date);
    }
    
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException {
        return projectmanager.getAllProjects();
    }

    @Override
    public boolean projectExist(Project project) throws SQLException {
        return projectmanager.projectExist(project);
    }

    @Override
    public Project createProject(Project project) throws SQLException {
        return projectmanager.createProject(project);
    }

    @Override
    public Project getProject(Project project) throws SQLException {
        return projectmanager.getProject(project);
    }

    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        return taskmanager.getAllTasks();
    }

    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskmanager.taskExist(task);
    }

    @Override
    public Task createTask(Task task) throws SQLException
    {
        return taskmanager.createTask(task);
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        //change to update task
        return taskmanager.updateTask(task);
    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
       return taskmanager.getTask(task);
    }

    @Override
    public String convertSecToTimeString(int totalSec) {
        
        return taskmanager.convertSecToTimeString(totalSec);
    }

    @Override
    public Client getClient(Client client) throws SQLException 
    {
      return clientmanager.getClient(client);
    }

    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException 
    {
        return clientmanager.getAllClients();
    }

    @Override
    public boolean clientExist(Client client) throws SQLException {
        return clientmanager.clientExist(client);
    }

    @Override
    public Client createClient(Client client) throws SQLException {
        return clientmanager.createClient(client);
    }
    
    public boolean deleteTask(Task task) throws SQLException
    {
        return taskmanager.deleteTask(task);
    }

    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskmanager.clearTask(task);
    }

    @Override
    public boolean deleteProject(Project project) throws SQLException {
       return projectmanager.deleteProject(project);
    }

    @Override
    public boolean deleteClient(Client client) throws SQLException {
       return clientmanager.deleteClient(client);
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userManager.updateUser(oldUser, newUser);
    }

    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskmanager.updateInterval(oldInterval, newInterval);
    }
    
    @Override
    public boolean deleteUser(User user) throws SQLException {
       return userManager.deleteUser(user);
    }

    @Override
    public boolean deleteInterval(Interval interval) throws SQLException 
    {
        return taskmanager.deleteInterval(interval);
    }
    
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        return clientmanager.updateClient(client);
    }

    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        return projectmanager.updateProject(project);
    }

    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
      return userManager.getAllUserProjects(user);
    }

    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return taskmanager.getAllTasks4Project(project);
    }

    @override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projectmanager.getProjectsForClient(client);
    }
    
    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
       return userManager.addUserToProject(user, project);
    }

    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
         return userManager.deleteProjectFromUser(user, project);
    }

    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
      return taskmanager.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

}
