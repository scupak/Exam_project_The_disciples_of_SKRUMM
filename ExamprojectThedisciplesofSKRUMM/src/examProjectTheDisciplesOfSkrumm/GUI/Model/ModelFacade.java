/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ClientModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ProjectModelInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import java.io.IOException;
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
    
    private ModelFacade() 
    {
        taskmodel = new TaskModel();
        projectmodel = new ProjectModel();
        clientmodel = new ClientModel();
    }
    
    /**
     * Utilizing the singleton pattern to make sure there is only one instance
     * of modelFacade.
     *
     * @return modelfacade
     * @throws java.io.IOException
     */
    public static ModelFacadeInterface getInstance() throws IOException, Exception
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
    public TreeItem<Task> getMockModel() 
    {
        return taskmodel.getMockModel();
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
    
}
