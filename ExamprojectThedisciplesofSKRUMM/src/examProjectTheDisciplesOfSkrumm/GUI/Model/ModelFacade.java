/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
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
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class ModelFacade implements ModelFacadeInterface
{
    private static ModelFacadeInterface modelfacade = null;
    private TaskModelInterface taskmodel;
    private ProjectModelInterface projectmodel;
    private ClientModelInterface clientmodel;
    private UserModelInterface userModel;
    
    private ModelFacade() throws IOException 
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
        if (modelfacade == null)
        {
            modelfacade = new ModelFacade();
        }
        return modelfacade;
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
    
}
