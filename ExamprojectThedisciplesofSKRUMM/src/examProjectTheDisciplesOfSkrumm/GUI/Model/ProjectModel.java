/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model;

import examProjectTheDisciplesOfSkrumm.BE.Client;
import examProjectTheDisciplesOfSkrumm.BE.Project;
import examProjectTheDisciplesOfSkrumm.GUI.Model.Interface.ProjectModelInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author kacpe
 */
public class ProjectModel implements ProjectModelInterface
{
    ObservableList<Project> projects;

    public ProjectModel()
    {
        this.projects = FXCollections.observableArrayList();
    }

    public ObservableList<Project> getProjects() 
    {
        return projects;
    }

    public void setProjects(ObservableList<Project> projects) 
    {
        this.projects = projects;
    }

    @Override
    public void CreateProject(Project project) 
    {
       projects.add(project);
    }
    
    
    
}
