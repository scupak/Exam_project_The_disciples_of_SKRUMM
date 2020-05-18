/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Interval;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BE.User;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ClientDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.DALFacadeInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.LogDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.ProjectDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.TaskDBDAOInterface;
import examProjectTheDisciplesOfSkrumm.DAL.Interface.UserDBDAOInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author SKRUMM
 */
public class DALFacade implements DALFacadeInterface
{
    private UserDBDAOInterface userDBDAO;
    private ClientDBDAOInterface clientDBDAO;
    private ProjectDBDAOInterface projecDBDAO;
    private TaskDBDAOInterface taskDBDAO;
    private LogDBDAOInterface  logDBDAO;


    public DALFacade() throws IOException, Exception
    {
        userDBDAO = new UserDBDAO();
        clientDBDAO = new ClientDBDAO();
        projecDBDAO = new ProjectDBDAO();
        taskDBDAO = new TaskDBDAO();
        logDBDAO = new LogDBDAO();
    }
    
    /**
     * gets a list of all the users
     * @return
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException
    {
        return userDBDAO.getAllUsers();
    }

    /**
     * checks if the user already exists, based on the email
     * @param user
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean userExist(User user) throws SQLException
    {
        return userDBDAO.userExist(user);
    }

    /**
     * creates a user
     * @param user
     * @return the new user
     * @throws SQLException 
     */
    @Override
    public User createUser(User user) throws SQLException
    {
        return userDBDAO.createUser(user);
    }

    /**
     * gets a user
     * @param user
     * @return the user
     * @throws SQLException 
     */
    @Override
    public User getUser(User user) throws SQLException
    {
        return userDBDAO.getUser(user);
    }

    /**
     * updates the password for a user
     * @param user
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException
    {
        return userDBDAO.updateUserPassword(user);
    }

    /**
     * gets a client
     * @param client
     * @return the client
     * @throws SQLException 
     */
    @Override
    public Client getClient(Client client) throws SQLException
    {
        return clientDBDAO.getClient(client);
    }

    /**
     * gets a list of all the clients
     * @return a list of all the clients
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException
    {
        return clientDBDAO.getAllClients();
    }

    /**
     * checks if the client already exists
     * @param client
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clientExist(Client client) throws SQLException
    {
        return clientDBDAO.clientExist(client);
    }

    /**
     * creates a client
     * @param client
     * @return the newly created client
     * @throws SQLException 
     */
    @Override
    public Client createClient(Client client) throws SQLException
    {
        return clientDBDAO.createClient(client);
    }
    /**
     * gets a list of all the projects
     * @return a list of all the projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException 
    {
       return projecDBDAO.getAllProjects();
    }

    /**
     * checks if the project already exists
     * @param project
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean projectExist(Project project) throws SQLException {
        return projecDBDAO.projectExist(project);
    }

    /**
     * creates a project
     * @param project
     * @return the newly created project
     * @throws SQLException 
     */
    @Override
    public Project createProject(Project project) throws SQLException {
        return projecDBDAO.createProject(project);
    }

    /**
     * gets a project
     * @param project
     * @return the project
     * @throws SQLException 
     */
    @Override
    public Project getProject(Project project) throws SQLException {
        return projecDBDAO.getProject(project);
    }

    /**
     * gets a list of all tasks
     * @return a list of all tasks
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        return taskDBDAO.getAllTasks();
    }

    /**
     * checks if a task already exists
     * @param task
     * @return true if it already exists, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskDBDAO.taskExist(task);
    }

    /**
     * creates a task
     * @param task
     * @return the task
     * @throws SQLException 
     */
    @Override
    public Task createTask(Task task) throws SQLException
    {
        return taskDBDAO.createTask(task);
    }

    /**
     * updates a task with new information
     * @param task
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        return taskDBDAO.updateTask(task);
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
        return taskDBDAO.getTask(task);
    }

    /**
     * makes a new interval
     * @param interval
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        taskDBDAO.newInterval(interval);
    }
    
    /**
     * updates the old interval to a new one
     * @param oldInterval
     * @param newInterval
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskDBDAO.updateInterval(oldInterval, newInterval);
    }

    /**
     * gets the six most recent tasks for a user as a list
     * @param user
     * @return the six most recent tasks for a user as a list
     * @throws SQLException 
     */
    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        return taskDBDAO.getSixTasks(user);
    }

    /**
     * gets all the tasks for a user as a list
     * @param user
     * @param date
     * @return all the tasks for a user as a list
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskDBDAO.getTasksForUser(user, date);
    }

    /**
     * deletes a task
     * @param task
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
      return taskDBDAO.deleteTask(task);
    }

    /**
     * clears a task
     * @param task
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskDBDAO.clearTask(task);
    }

    /**
     * deletes a project
     * @param project
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteProject(Project project) throws SQLException {
        return projecDBDAO.deleteProject(project);
    }

    /**
     * clears a project
     * @param project
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean clearProject(Project project) throws SQLException {
        return projecDBDAO.clearProject(project);
    }
    
    /**
     * deletes a client
     * @param client
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    @Override
    public boolean deleteClient(Client client) throws SQLException {
        return clientDBDAO.deleteClient(client);
    }

    /**
     * updates a user with new information
     * @param oldUser
     * @param newUser
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userDBDAO.updateUser(oldUser, newUser);
    }

    /**
     * gets a list of all the projects for a client
     * @param client
     * @return a list of all the projects for a client
     * @throws SQLException 
     */
    @Override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projecDBDAO.getProjectsForClient(client);
    }
    
    /**
     * deletes an interval
     * @param interval
     * @return true if the operation was successful, false otherwise
     * @throws SQLException 
     */
    public boolean deleteInterval(Interval interval) throws SQLException {
       return taskDBDAO.deleteInterval(interval);
    }
    
    /**
     * deletes a user
     * @param user
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteUser(User user) throws SQLServerException, SQLException {
        return userDBDAO.deleteUser(user);
    }

    /**
     * updates a client with new information
     * @param client
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        return clientDBDAO.updateClient(client);
    }

    /**
     * updates a project with new information
     * @param project
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        return projecDBDAO.updateProject(project);
    }

    /**
     * gets all the users assigned projects
     * @param user
     * @return a list of all the users assigned projects
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return userDBDAO.getAllUserProjects(user);
    }

    /**
     * adds a user to a project
     * @param user
     * @param project
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
        return userDBDAO.addUserToProject(user, project);
    }

    /**
     * deletes a project from a user
     * @param user
     * @param project
     * @return true if the operation was successful, false otherwise
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
        return userDBDAO.deleteProjectFromUser(user, project);
    }

    /**
     * gets all tasks for a project as a list
     * @param project
     * @return all tasks for a project as a list
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return taskDBDAO.getAllTasks4Project(project);
    }

    /**
     * gets a list of all tasks between two dates for the given user
     * @param user
     * @param fromdate
     * @param todate
     * @return a list of all tasks between two dates for the given user
     * @throws SQLException 
     */
    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
        return taskDBDAO.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    /**
     * gets the duration of tasks
     * @param project
     * @return the duration of tasks
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        return taskDBDAO.getDurationFromTasks(project);
    }

    /**
     * gets the duration of intervals between two dates
     * @param userID
     * @param projectID
     * @param fromdate
     * @param todate
     * @return the duration of intervals between two dates
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
       return taskDBDAO.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }

    /**
     * gets the 100 most recent logs
     * @return the 100 most recent logs
     * @throws SQLServerException
     * @throws SQLException 
     */
    @Override
    public List<String> getAllLogs() throws SQLServerException, SQLException {
       return logDBDAO.getAllLogs();
    }

    /**
     * creates a log entry
     * @param description
     * @return the  new log entry
     * @throws SQLException 
     */
    @Override
    public String createLog(String description) throws SQLException {
        return logDBDAO.createLog(description);
    }
    
}
