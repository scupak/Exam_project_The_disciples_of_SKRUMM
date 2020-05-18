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
import examProjectTheDisciplesOfSkrumm.BLL.Interface.ViewFactoryInterface;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TreeTableUtil;
import examProjectTheDisciplesOfSkrumm.BLL.Util.DALFacadeFactory;
import examProjectTheDisciplesOfSkrumm.BLL.Util.ViewFactory;
import examProjectTheDisciplesOfSkrumm.enums.DALFacadeTypes;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.XYChart;
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
    private ViewFactoryInterface viewfactory;

    public BLLFacade() throws IOException, Exception 
    {
        projectmanager = new ProjectManager(DALFacadeFactory.CreateDALFacade(DALFacadeTypes.PRODUCTION));
        treeTableUtil = new TreeTableUtil();
        taskmanager = new TaskManager(DALFacadeFactory.CreateDALFacade(DALFacadeTypes.PRODUCTION));
        securityManager = new examProjectTheDisciplesOfSkrumm.BLL.Security.SecurityManager();
        userManager = new UserManager(DALFacadeFactory.CreateDALFacade(DALFacadeTypes.PRODUCTION));
        clientmanager = new ClientManager(DALFacadeFactory.CreateDALFacade(DALFacadeTypes.PRODUCTION));
        viewfactory = new ViewFactory();
    }

    /**
     * 
     * @param tasks
     * @return 
     */
    @Override
    public TreeItem<Task> getModel(ObservableList<Task> tasks) {
        return treeTableUtil.getModel(tasks);
    }

   

    /**
     * hashes a users password
     * @param password
     * @return string
     * @throws SecurityException 
     */
    @Override
    public String hashPassword(String password) throws SecurityException 
    {
        return securityManager.hashPassword(password);
    }

    /**
     * checks is a user exists
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public Boolean checkUser(User user) throws SQLException
    {
        return userManager.checkUser(user);
    }

    /**
     * hashes passwords
     * @param user
     * @throws SQLException 
     */
    @Override
    public void passwordHash(User user) throws SQLException
    {
        userManager.passwordHash(user);
    }

    /**
     * gets a specific user
     * @param user
     * @return user
     * @throws SQLException 
     */
    @Override
    public User getUser(User user) throws SQLException
    {
        return userManager.getUser(user);
    }

    /**
     * gets all users
     * @return List users
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException {
        return userManager.getAllUsers();
    }

    /**
     * checks if user exists
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean userExist(User user) throws SQLException {
        return userManager.userExist(user);
    }

    /**
     * creates a new user
     * @param user
     * @return user
     * @throws SQLException 
     */
    @Override
    public User createUser(User user) throws SQLException {
        return userManager.createUser(user);
    }

    /**
     * creates a new interval
     * @param interval
     * @throws SQLException 
     */
    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        taskmanager.newInterval(interval);
    }

    /**
     * gets the six most recent tasks based on a tasks lastUsed
     * @param user
     * @return List tasks
     * @throws SQLException 
     */
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
       return taskmanager.getSixTasks(user);
    }

    /**
     * gets all tasks for a user 
     * @param user
     * @param date
     * @return List taks
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskmanager.getTasksForUser(user, date);
    }
    
    /**
     * Gets all projects
     * @return List projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException {
        return projectmanager.getAllProjects();
    }

    /**
     * checks if a project exists
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean projectExist(Project project) throws SQLException {
        return projectmanager.projectExist(project);
    }

    /**
     * creates a new project
     * @param project
     * @return project
     * @throws SQLException 
     */
    @Override
    public Project createProject(Project project) throws SQLException {
        return projectmanager.createProject(project);
    }

    /**
     * gets a specific project
     * @param project
     * @return project
     * @throws SQLException 
     */
    @Override
    public Project getProject(Project project) throws SQLException {
        return projectmanager.getProject(project);
    }

    /**
     * gets all the tasks
     * @return Lists tasks
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        return taskmanager.getAllTasks();
    }

    /**
     * checks if a task exists
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskmanager.taskExist(task);
    }

    /**
     * creates a new task
     * @param task
     * @return task
     * @throws SQLException 
     */
    @Override
    public Task createTask(Task task) throws SQLException
    {
        return taskmanager.createTask(task);
    }

    /**
     * updates an exsiting task
     * @param task
     * @return task
     * @throws SQLException 
     */
    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        //change to update task
        return taskmanager.updateTask(task);
    }

    /**
     * gets a specific task
     * @param task
     * @return task
     * @throws SQLException 
     */
    @Override
    public Task getTask(Task task) throws SQLException
    {
       return taskmanager.getTask(task);
    }

    /**
     * converts a tasks total time from seconds to hours and minutes
     * @param totalSec
     * @return string
     */
    @Override
    public String convertSecToTimeString(int totalSec) {
        
        return taskmanager.convertSecToTimeString(totalSec);
    }

    /**
     * gets a specific client
     * @param client
     * @return client
     * @throws SQLException 
     */
    @Override
    public Client getClient(Client client) throws SQLException 
    {
      return clientmanager.getClient(client);
    }

    /**
     * gets all clients
     * @return List clients
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException 
    {
        return clientmanager.getAllClients();
    }

    /**
     * checks if a client exists
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean clientExist(Client client) throws SQLException {
        return clientmanager.clientExist(client);
    }

    /**
     * creates a new client
     * @param client
     * @return client
     * @throws SQLException 
     */
    @Override
    public Client createClient(Client client) throws SQLException {
        return clientmanager.createClient(client);
    }
    
    /**
     * deletes a task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return taskmanager.deleteTask(task);
    }

    /**
     * removes all intervales for a given task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskmanager.clearTask(task);
    }

    /**
     * deletes a project
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteProject(Project project) throws SQLException {
       return projectmanager.deleteProject(project);
    }

    /**
     * deletes a client 
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
       return clientmanager.deleteClient(client);
    }

    /**
     * updates an exsisting user
     * @param oldUser
     * @param newUser
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userManager.updateUser(oldUser, newUser);
    }

    /**
     * updates an exisiting interval
     * @param oldInterval
     * @param newInterval
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskmanager.updateInterval(oldInterval, newInterval);
    }
    
    /**
     * deletes a user
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteUser(User user) throws SQLException {
       return userManager.deleteUser(user);
    }

    /**
     * deletes an interval 
     * @param interval
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException 
    {
        return taskmanager.deleteInterval(interval);
    }
    
    /**
     * updates an existing client
     * @param client
     * @return client
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        return clientmanager.updateClient(client);
    }

    /**
     * updates an existing project
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        return projectmanager.updateProject(project);
    }

    /**
     * gets all users for a project
     * @param user
     * @return List users
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
      return userManager.getAllUserProjects(user);
    }

    /**
     * gets all tasks for a project
     * @param project
     * @return List projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return taskmanager.getAllTasks4Project(project);
    }

    /**
     * gets all projects for a client
     * @param client
     * @return List projects
     * @throws SQLException 
     */
    @Override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projectmanager.getProjectsForClient(client);
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
       return userManager.addUserToProject(user, project);
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
         return userManager.deleteProjectFromUser(user, project);
    }

    /**
     * gets tasks for a user between two dates
     * @param user
     * @param fromdate
     * @param todate
     * @return list tasks
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
      return taskmanager.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    /**
     * gets the duration for a project
     * @param project
     * @return int
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        return taskmanager.getDurationFromTasks(project);
    }

    /**
     * gets duration for intavles between to dates and addes them togehter
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return int
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
       return taskmanager.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }

    /**
     * handles the users bar chart
     * @param userID
     * @param fromdate
     * @param todate
     * @return barchart data
     * @throws SQLException 
     */
    @Override
    public XYChart.Series handleProjectBarChartData(String userID, LocalDate fromdate, LocalDate todate) throws SQLException {
        return taskmanager.handleProjectBarChartData(userID, fromdate, todate);
    }

    /**
     * handles the admins bar chart
     * @param projectID
     * @param fromdate
     * @param todate
     * @return barchart
     * @throws SQLException 
     */
    @Override
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID, LocalDate fromdate, LocalDate todate) throws SQLException {
        return taskmanager.handleProjectBarChartDataForAdmin(projectID, fromdate, todate);
    }

    @Override
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException 
    {
        return viewfactory.getLoader(viewtype);
    }

    public List<String> getAllLogs() throws SQLServerException, SQLException {
       return userManager.getAllLogs();
    }

}
