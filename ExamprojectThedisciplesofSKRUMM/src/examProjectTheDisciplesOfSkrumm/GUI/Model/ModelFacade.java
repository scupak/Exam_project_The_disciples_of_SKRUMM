/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.BLL.Util.TimerUtil;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ClientModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ProjectModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.UserModelInterface;
import examProjectTheDisciplesOfSkrumm.enums.UserMode;
import examProjectTheDisciplesOfSkrumm.enums.ViewTypes;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;

/**
 *
 * @author SKRUMM
 */
public class ModelFacade implements ModelFacadeInterface
{
    private static volatile ModelFacadeInterface modelfacade = null;
    private TaskModelInterface taskmodel;
    private ProjectModelInterface projectmodel;
    private ClientModelInterface clientmodel;
    private UserModelInterface userModel;
    
    public ModelFacade() throws IOException, SQLException, Exception 
    {
        taskmodel = new TaskModel();
        projectmodel = new ProjectModel();
        clientmodel = new ClientModel();
        userModel = new UserModel();
        
    }
    
    /**
     * Utilizing the singleton pattern to make sure there is only one instance
     * of modelFacade.
     *
     * @return modelfacade
     * @throws java.io.IOException
     */
  synchronized public static ModelFacadeInterface getInstance() throws IOException, Exception
    {
        ModelFacadeInterface instance = ModelFacade.modelfacade;
        if (instance == null) // First check (no locking)
        {
              synchronized(ModelFacade.class){ 
                instance = ModelFacade.modelfacade;
                  
                 if (instance == null) // Second check (with locking)
        { 
                  
                    ModelFacade.modelfacade = instance = new ModelFacade();            
              }
              }
        }
        return instance;
    }
    
  /**
   * gets all the tasks for the user and puts them in a root treeitem, and sets this up to the tree table
   * @param user
   * @param fromdate
   * @param todate
   * @return all the tasks for the user and puts them in a root treeitem
   */
    @Override
    public TreeItem<Task> getModel(User user, LocalDate fromdate, LocalDate todate) {
        return taskmodel.getModel( user, fromdate, todate);
    }

    /**
     * creates task
     * @param task 
     */
    @Override
    public void createTask(Task task) {
        taskmodel.createTask(task);
    }

    /**
     * gets projects
     * @return projects as a list
     */
    @Override
    public ObservableList<Project> getProjects() 
    {
        return projectmodel.getProjects();
    }

    /**
     * sets projects to the given list
     * @param projects 
     */
    @Override
    public void setProjects(ObservableList<Project> projects) 
    {
        projectmodel.setProjects(projects);
    }

    /**
     * creates a project
     * @param project 
     */
    @Override
    public void CreateProject(Project project) {
        projectmodel.CreateProject(project);
    }

    /**
     * gets clients
     * @return clients as a list 
     */
    @Override
    public ObservableList<Client> getClients() 
    {
        return clientmodel.getClients();
    }

    /**
     * sets clients to the given list
     * @param clients 
     */
    @Override
    public void setClients(ObservableList<Client> clients) {
        clientmodel.setClients(clients);
    }

    /**
     * creates a client
     * @param client 
     */
    @Override
    public void createClient(Client client) {
        clientmodel.createClient(client);
    }

    /**
     * gets tasks
     * @return tasks as a list
     */
    @Override
    public ObservableList<Task> getTasks() 
    {
        return taskmodel.getTasks();
    }

    /**
     * sets tasks
     * @param tasks 
     */
    @Override
    public void setTasks(ObservableList<Task> tasks) {
        taskmodel.setTasks(tasks);
    }

    /**
     * checks the if a give user matches with a user in the database
     * @param user
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean checkUser(User user) throws SQLException
    {
        return userModel.checkUser(user);
    }

    /**
     * hashes a password
     * @param password
     * @return the hashed password
     */
    @Override
    public String hashPassword(String password)
    {
        return userModel.hashPassword(password);
    }

    /**
     * gets a user
     * @param user
     * @return the user
     * @throws SQLException 
     */
    @Override
    public User getUser(User user) throws SQLException {
        return userModel.getUser(user);
    }

    /**
     * gets all users
     * @return all users as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLServerException, SQLException {
        return userModel.getAllUsers();
    }

    /**
     * checks if the user exists
     * @param user
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean userExist(User user) throws SQLException {
        return userModel.userExist(user);
    }

    /**
     * creates user
     * @param user
     * @return the new user
     * @throws SQLException 
     */
    @Override
    public User createUser(User user) throws SQLException {
       return userModel.createUser(user);
    }

    /**
     * gets current user
     * @return current user
     */
    @Override
    public User getCurrentuser() {
        return userModel.getCurrentuser();
    }

    /**
     * sets current user
     * @param currentuser 
     */
    @Override
    public void setCurrentuser(User currentuser) {
        userModel.setCurrentuser(currentuser);
    }
    
    /**
     * makes a new interval
     * @param interval
     * @throws SQLException 
     */
    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        taskmodel.newInterval(interval);
    }
    
    /**
     * updates interval
     * @param oldInterval
     * @param newInterval
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskmodel.updateInterval(oldInterval, newInterval);
    }

    /**
     * gets the six most recent tasks for the user
     * @param user
     * @return the six most recent tasks for the user as a list
     * @throws SQLException 
     */
    @Override
    public ObservableList<Task> getSixTasks(User user) throws SQLException
    {
        return taskmodel.getSixTasks(user);
    }

    /**
     * gets Tasks For the User
     * @param user
     * @param date
     * @return Tasks For the User as a list
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskmodel.getTasksForUser(user, date);
    }

    /**
     * gets All Tasks
     * @return All Tasks as a list
     * @throws SQLException 
     */
    @Override
    public ObservableList<Task> getAllTasks() throws SQLException
    {
        return taskmodel.getAllTasks();
    }

    /**
     * checks if the task exists
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskmodel.taskExist(task);
    }

    /**
     * updates a task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        return taskmodel.updateTask(task);
    }

    /**
     * gets a task
     * @param task
     * @return a task
     * @throws SQLException 
     */
    @Override
    public Task getTask(Task task) throws SQLException
    {
        return taskmodel.getTask(task);
    }

    /**
     * converts time in seconds to time in HH:MM:SS format
     * @param totalSec
     * @return time in HH:MM:SS format as a string
     */
    @Override
    public String convertSecToTimeString(int totalSec) {
        
       return taskmodel.convertSecToTimeString(totalSec);
    }

    /**
     * Deletes a task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return taskmodel.deleteTask(task);
    }

    /**
     * Clears a task
     * @param task
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskmodel.clearTask(task);
    }
    
    /**
     * Gets if the timer is running
     * @return boolean
     */
    @Override
    public boolean getisTimerRunning() {
       return taskmodel.getisTimerRunning();
    }

    /**
     * sets Timer to true or false to signify that it is or isn't running
     * @param isTimerRunning 
     */
    @Override
    public void setIsTimerRunning(boolean isTimerRunning) {
       taskmodel.setIsTimerRunning(isTimerRunning);
    }

    /**
     * gets Timer util
     * @return Timer util
     */
    @Override
    public TimerUtil getTimerutil() {
       return taskmodel.getTimerutil();
    }

    /**
     * sets Timerutil
     * @param timerutil 
     */
    @Override
    public void setTimerutil(TimerUtil timerutil) {
        taskmodel.setTimerutil(timerutil);
    }

    /**
     * deletes Project
     * @param project
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteProject(Project project) throws SQLException 
    {
        return projectmodel.deleteProject(project);
    }

    /**
     * gets ExecutorService, and its runnable
     * @return ExecutorService, and its runnable
     */
    @Override
    public ExecutorService getExecutorService() {
       return taskmodel.getExecutorService();
    }

    /**
     * sets ExecutorService
     * @param executorService 
     */
    @Override
    public void setExecutorService(ExecutorService executorService) {
           taskmodel.setExecutorService(executorService);
    }
    
    /**
     * deletes Client
     * @param client
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
        return clientmodel.deleteClient(client);
    }

    /**
     * updates User
     * @param oldUser
     * @param newUser
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userModel.updateUser(oldUser, newUser);
    }

    /**
     * gets All Projects
     * @return All Projects as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException
    {
        return projectmodel.getAllProjects();
    }
    
    /**
     * deletes Interval
     * @param interval
     * @return boolean
     * @throws SQLException 
     */
    @Override
    public boolean deleteInterval(Interval interval) throws SQLException {
        return taskmodel.deleteInterval(interval);
    }
    
    /**
     * deletes User
     * @param user
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteUser(User user) throws SQLServerException, SQLException {
        return userModel.deleteUser(user);
    }

    /**
     * updates Project
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException
    {
        return  projectmodel.updateProject(project);
    }

    /**
     * updates Client
     * @param client
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException
    {
      return clientmodel.updateClient(client);
    }

    /**
     * gets All User Projects
     * @param user
     * @returnAll User Projects as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return userModel.getAllUserProjects(user);
    }

    /**
     * gets All Tasks for a Project
     * @param project
     * @return All Tasks for a Project
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return taskmodel.getAllTasks4Project(project);
    }

    /**
     * adds User To a Project
     * @param user
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
       return userModel.addUserToProject(user, project);
    }
    
    /**
     * gets Projects For the Client
     * @param client
     * @return Projects For the Client as a list
     * @throws SQLException 
     */
    @Override
    public ObservableList<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projectmodel.getProjectsForClient(client);
    }

    /**
     * deletes Project From User
     * @param user
     * @param project
     * @return boolean
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
        return userModel.deleteProjectFromUser(user, project);
    }

    /**
     * gets Tasks For User between two Dates
     * @param user
     * @param fromdate
     * @param todate
     * @return Tasks For User between two Dates
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
        return taskmodel.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    /**
     * sets Current User Mode
     * @param userMode 
     */
    @Override
    public void setCurrentUserMode(UserMode userMode)
    {
       userModel.setCurrentUserMode(userMode);
    }

    /**
     * gets Current User Mode
     * @return Current User Mode
     */
    @Override
    public UserMode getCurrentUserMode()
    {
        return userModel.getCurrentUserMode();
    }

    /**
     * gets Duration From Intervals between two Dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return Duration From Intervals between two Dates
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
       return  taskmodel.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }

    /**
     * Handles the bar chart data
     * @param userID
     * @param fromdate
     * @param todate
     * @return Handles the bar chart data
     * @throws SQLException 
     */
    @Override
    public XYChart.Series handleProjectBarChartData(String userID, LocalDate fromdate, LocalDate todate) throws SQLException {
        return projectmodel.handleProjectBarChartData(userID, fromdate, todate);
    }

    /**
     * Handles the bar chart data for admin
     * @param projectID
     * @param fromdate
     * @param todate
     * @return Handles the bar chart data for admin
     * @throws SQLException 
     */
    @Override
    public XYChart.Series handleProjectBarChartDataForAdmin(int projectID, LocalDate fromdate, LocalDate todate) throws SQLException {
        return projectmodel.handleProjectBarChartDataForAdmin(projectID, fromdate, todate);
    }
    public void setCurrentAdmin(User currentAdmin)
    {
        userModel.setCurrentAdmin(currentAdmin);
    }

    /**
     * gets the current admin
     * @return the current admin
     */
    @Override
    public User getCurrentAdmin()
    {
       return userModel.getCurrentAdmin();
    }

    /**
     * gets the FXML loader
     * @param viewtype
     * @return the FXML loader
     * @throws Exception
     * @throws IOException 
     */
    @Override
    public FXMLLoader getLoader(ViewTypes viewtype) throws Exception, IOException {
        return taskmodel.getLoader(viewtype);
    }

    /**
     * gets the 100 most recent logs
     * @return the 100 most recent logs
     * @throws SQLServerException
     * @throws SQLException 
     */
    public ObservableList<String> getAllLogs() throws SQLServerException, SQLException {
        
        
        return userModel.getAllLogs();
    }
    
}
