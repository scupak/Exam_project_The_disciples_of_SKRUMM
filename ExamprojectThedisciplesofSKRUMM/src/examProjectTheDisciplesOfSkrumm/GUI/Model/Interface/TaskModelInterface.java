/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.BE.Task;
import javafx.scene.control.TreeItem;

/**
 *
 * @author kacpe
 */
public interface TaskModelInterface 
{
    public TreeItem<Task> getMockModel();
    
    public TreeItem<Task> getModel(String title, Project project, Client client, int time);
    
}
