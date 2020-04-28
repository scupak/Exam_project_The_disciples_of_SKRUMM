/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import examProjectTheDisciplesOfSkrumm.BLL.BLLFacade;
import examProjectTheDisciplesOfSkrumm.BLL.Interface.BLLFacadeInterface;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.TaskModelInterface;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public class TaskModel implements TaskModelInterface
{
    private BLLFacadeInterface bllfacade;

    TaskModel()
    {
        bllfacade = new BLLFacade();
    }

    @Override
    public TreeItem<Task> getMockModel() {
        return bllfacade.getMockModel();
    }

    @Override
    public TreeItem<Task> getModel(String title, Project project, Client client, int time) {
        return bllfacade.getModel(title, project, client, time);
    }
    
    
}


