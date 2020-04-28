/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ModelFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import java.io.IOException;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class ModelFacade implements ModelFacadeInterface
{
    private static ModelFacadeInterface modelfacade = null;
    private TaskModelInterface taskmodel;
    
    private ModelFacade() 
    {
        taskmodel = new TaskModel();
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
        if (modelfacade == null)
        {
            modelfacade = new ModelFacade();
        }
        return modelfacade;
    }
    

    @Override
    public TreeItem<Task> getMockModel() 
    {
        return taskmodel.getMockModel();
    }

    @Override
    public TreeItem<Task> getModel(String title, Project project, Client client, int time) {
        return taskmodel.getModel(title, project, client, time);
    }
    
}
