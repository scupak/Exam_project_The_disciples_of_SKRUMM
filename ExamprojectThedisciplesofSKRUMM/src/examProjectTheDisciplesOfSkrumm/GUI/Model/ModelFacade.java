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
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ClientModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ProjectModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.UserModelInterface;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    
    private ModelFacade() throws IOException, SQLException 
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
    public TreeItem<Task> getModel() {
        return taskmodel.getModel();
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
    public List<Task> getAllTasks() throws SQLException
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
    
    
    
}
