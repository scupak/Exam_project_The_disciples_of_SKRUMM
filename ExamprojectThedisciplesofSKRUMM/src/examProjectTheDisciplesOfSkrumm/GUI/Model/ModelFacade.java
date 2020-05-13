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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class ModelFacade implements ModelFacadeInterface
{
    private static volatile ModelFacadeInterface modelfacade = null;
    private TaskModelInterface taskmodel;
    private ProjectModelInterface projectmodel;
    private ClientModelInterface clientmodel;
    private UserModelInterface userModel;
    
    public ModelFacade() throws IOException, SQLException 
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
    

   

    @Override
    public TreeItem<Task> getModel(User user, LocalDate date) {
        return taskmodel.getModel( user, date);
    }

    @Override
    public void createTask(Task task) {
        taskmodel.createTask(task);
    }

    @Override
    public ObservableList<Project> getProjects() 
    {
        return projectmodel.getProjects();
    }

    @Override
    public void setProjects(ObservableList<Project> projects) 
    {
        projectmodel.setProjects(projects);
    }

    @Override
    public void CreateProject(Project project) {
        projectmodel.CreateProject(project);
    }

    @Override
    public ObservableList<Client> getClients() 
    {
        return clientmodel.getClients();
    }

    @Override
    public void setClients(ObservableList<Client> clients) {
        clientmodel.setClients(clients);
    }

    @Override
    public void createClient(Client client) {
        clientmodel.createClient(client);
    }

    @Override
    public ObservableList<Task> getTasks() 
    {
        return taskmodel.getTasks();
    }

    @Override
    public void setTasks(ObservableList<Task> tasks) {
        taskmodel.setTasks(tasks);
    }

    /**
     * checks the if a give user maches with a user in the database
     * @param user
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean checkUser(User user) throws SQLException
    {
        return userModel.checkUser(user);
    }

    @Override
    public String hashPassword(String password)
    {
        return userModel.hashPassword(password);
    }

    @Override
    public User getUser(User user) throws SQLException {
        return userModel.getUser(user);
    }

    @Override
    public ObservableList<User> getAllUsers() throws SQLServerException, SQLException {
        return userModel.getAllUsers();
    }

    @Override
    public boolean userExist(User user) throws SQLException {
        return userModel.userExist(user);
    }

    @Override
    public User createUser(User user) throws SQLException {
       return userModel.createUser(user);
    }

    @Override
    public User getCurrentuser() {
        return userModel.getCurrentuser();
    }

    @Override
    public void setCurrentuser(User currentuser) {
        userModel.setCurrentuser(currentuser);
    }
    
    @Override
    public void newInterval(Interval interval) throws SQLException
    {
        taskmodel.newInterval(interval);
    }
    
    @Override
    public boolean updateInterval( Interval oldInterval, Interval newInterval) throws SQLServerException, SQLException
    {
        return taskmodel.updateInterval(oldInterval, newInterval);
    }

    @Override
    public ObservableList<Task> getSixTasks(User user) throws SQLException
    {
        return taskmodel.getSixTasks(user);
    }

    @Override
    public List<Task> getTasksForUser(User user, LocalDate date) throws SQLException
    {
        return taskmodel.getTasksForUser(user, date);
    }

    @Override
    public ObservableList<Task> getAllTasks() throws SQLException
    {
        return taskmodel.getAllTasks();
    }

    @Override
    public boolean taskExist(Task task) throws SQLException
    {
        return taskmodel.taskExist(task);
    }

    @Override
    public Boolean updateTask(Task task) throws SQLException
    {
        return taskmodel.updateTask(task);
    }

    @Override
    public Task getTask(Task task) throws SQLException
    {
        return taskmodel.getTask(task);
    }

    @Override
    public String convertSecToTimeString(int totalSec) {
        
       return taskmodel.convertSecToTimeString(totalSec);
    }

    @Override
    public boolean deleteTask(Task task) throws SQLException
    {
        return taskmodel.deleteTask(task);
    }

    @Override
    public boolean clearTask(Task task) throws SQLException
    {
        return taskmodel.clearTask(task);
    }
    
    public boolean getisTimerRunning() {
       return taskmodel.getisTimerRunning();
    }

    @Override
    public void setIsTimerRunning(boolean isTimerRunning) {
       taskmodel.setIsTimerRunning(isTimerRunning);
    }

    @Override
    public TimerUtil getTimerutil() {
       return taskmodel.getTimerutil();
    }

    @Override
    public void setTimerutil(TimerUtil timerutil) {
        taskmodel.setTimerutil(timerutil);
    }

    @Override
    public boolean deleteProject(Project project) throws SQLException 
    {
        return projectmodel.deleteProject(project);
    }

    @Override
    public ExecutorService getExecutorService() {
       return taskmodel.getExecutorService();
    }

    @Override
    public void setExecutorService(ExecutorService executorService) {
           taskmodel.setExecutorService(executorService);
    }
    
    public boolean deleteClient(Client client) throws SQLException {
        return clientmodel.deleteClient(client);
    }

    @Override
    public boolean updateUser(User oldUser, User newUser) throws SQLServerException, SQLException {
        return userModel.updateUser(oldUser, newUser);
    }

    @Override
    public List<Project> getAllProjects() throws SQLServerException, SQLException
    {
        return projectmodel.getAllProjects();
    }
    
    public boolean deleteInterval(Interval interval) throws SQLException {
        return taskmodel.deleteInterval(interval);
    }
    
    @Override
    public boolean deleteUser(User user) throws SQLServerException, SQLException {
        return userModel.deleteUser(user);
    }

    @Override
    public boolean updateProject(Project project) throws SQLServerException, SQLException
    {
        return  projectmodel.updateProject(project);
    }

    @Override
    public boolean updateClient(Client client) throws SQLServerException, SQLException
    {
      return clientmodel.updateClient(client);
    }

    @Override
    public List<Project> getAllUserProjects(User user) throws SQLServerException, SQLException {
        return userModel.getAllUserProjects(user);
    }

    @Override
    public boolean addUserToProject(User user, Project project) throws SQLServerException, SQLException {
       return userModel.addUserToProject(user, project);
    }
    
    public ObservableList<Project> getProjectsForClient(Client client) throws SQLException
    {
        return projectmodel.getProjectsForClient(client);
    }
    
}
