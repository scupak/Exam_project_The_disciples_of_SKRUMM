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
 * @author lumby
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
    
    @Override
    public List<User> getAllUsers() throws SQLServerException, SQLException
    {
        return userDBDAO.getAllUsers();
    }

    @Override
    public boolean userExist(User user) throws SQLException
    {
        return userDBDAO.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException
    {
        return userDBDAO.createUser(user);
    }

    @Override
    public User getUser(User user) throws SQLException
    {
        return userDBDAO.getUser(user);
    }

    @Override
    public boolean updateUserPassword(User user) throws SQLServerException, SQLException
    {
        return userDBDAO.updateUserPassword(user);
    }

    @Override
    public Client getClient(Client client) throws SQLException
    {
        return clientDBDAO.getClient(client);
    }

    @Override
    public List<Client> getAllClients() throws SQLServerException, SQLException
    {
        return clientDBDAO.getAllClients();
    }

    @Override
    public boolean clientExist(Client client) throws SQLException
    {
        return clientDBDAO.clientExist(client);
    }

    @Override
    public Client createClient(Client client) throws SQLException
    {
        return clientDBDAO.createClient(client);
    }

    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException 
    {
       return projecDBDAO.getAllProjects();
    }

    @Override
    public boolean projectExist(Project project) throws SQLException {
        return projecDBDAO.projectExist(project);
    }

    @Override
    public Project createProject(Project project) throws SQLException {
        return projecDBDAO.createProject(project);
    }

    @Override
    public Project getProject(Project project) throws SQLException {
        return projecDBDAO.getProject(project);
    }

    @Override
    public List<Task> getAllTasks() throws SQLException
    {
        return taskDBDAO.getAllTasks();
    }

    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskDBDAO.taskExist(task);
    }

    @Override
    public Task createTask(Task task) throws SQLException
    {
        return taskDBDAO.createTask(task);
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        return taskDBDAO.updateTask(task);
    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
        return taskDBDAO.getTask(task);
    }

    @Override
    public void newInterval(Interval interval) throws SQLServerException, SQLException
    {
        taskDBDAO.newInterval(interval);
    }
    
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskDBDAO.updateInterval(oldInterval, newInterval);
    }

    @Override
    public List<Task> getSixTasks(User user) throws SQLException
    {
        return taskDBDAO.getSixTasks(user);
    }

    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskDBDAO.getTasksForUser(user, date);
    }

    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
      return taskDBDAO.deleteTask(task);
    }

    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskDBDAO.clearTask(task);
    }

    @Override
    public boolean deleteProject(Project project) throws SQLException {
        return projecDBDAO.deleteProject(project);
    }

    @Override
    public boolean clearProject(Project project) throws SQLException {
        return projecDBDAO.clearProject(project);
    }
    
    @Override
    public boolean deleteClient(Client client) throws SQLException {
        return clientDBDAO.deleteClient(client);
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userDBDAO.updateUser(oldUser, newUser);
    }

    @Override
    public List<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projecDBDAO.getProjectsForClient(client);
    }
    
    public boolean deleteInterval(Interval interval) throws SQLException {
       return taskDBDAO.deleteInterval(interval);
    }
    
    @Override
    public boolean deleteUser(User user) throws SQLServerException, SQLException {
        return userDBDAO.deleteUser(user);
    }

    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException {
        return clientDBDAO.updateClient(client);
    }

    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException {
        return projecDBDAO.updateProject(project);
    }

    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return userDBDAO.getAllUserProjects(user);
    }

    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
        return userDBDAO.addUserToProject(user, project);
    }

    @Override
    public boolean deleteProjectFromUser(User user, Project project) throws SQLServerException, SQLException {
        return userDBDAO.deleteProjectFromUser(user, project);
    }

    @Override
    public List<Task> getAllTasks4Project(Project project) throws SQLServerException, SQLException {
        return taskDBDAO.getAllTasks4Project(project);
    }

    @Override
    public List<Task> getTasksForUserbetween2Dates(User user, LocalDate fromdate, LocalDate todate) throws SQLException {
        return taskDBDAO.getTasksForUserbetween2Dates(user, fromdate, todate);
    }

    @Override
    public int getDurationFromTasks(Project project) throws SQLServerException, SQLException {
        return taskDBDAO.getDurationFromTasks(project);
    }

    @Override
    public int getDurationFromIntervalsbetween2Dates(String userID, int projectID, LocalDate fromdate, LocalDate todate) throws SQLServerException, SQLException {
       return taskDBDAO.getDurationFromIntervalsbetween2Dates(userID, projectID, fromdate, todate);
    }

    @Override
    public List<String> getAllLogs() throws SQLServerException, SQLException {
       return logDBDAO.getAllLogs();
    }

    @Override
    public String createLog(String userName, String action, String projectName, String taskName) throws SQLException {
        return logDBDAO.createLog(userName, action, projectName, taskName);
    }
    
}
