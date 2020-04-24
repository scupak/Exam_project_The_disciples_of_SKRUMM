/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author lumby
 */
public class Project
{
    private SimpleStringProperty projectName;
    private SimpleStringProperty clientName;
    
    public Project(String projectName, String clientName)
    {
        this.projectName = new SimpleStringProperty(projectName);
        this.clientName = new SimpleStringProperty(clientName);
    }

    public String getProjectName()
    {
        return projectName.get();
    }

    public String getClientName()
    {
        return clientName.get();
    }
    
}
