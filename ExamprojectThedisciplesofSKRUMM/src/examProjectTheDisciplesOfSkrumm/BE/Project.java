/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kacpe
 */
public class Project 
{
    private SimpleStringProperty projectName;
    private Client client;
    private SimpleStringProperty clientName;
    private SimpleIntegerProperty ProjectRate;

    public Project(String projectName, Client client, int projectrate) 
    {
        this.projectName = new SimpleStringProperty(projectName);
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
        this.ProjectRate = new SimpleIntegerProperty(projectrate);
    }

    public String getProjectName() {
        return projectName.get();
    }

    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
    }

    public String getClientName() {
        return clientName.get();
    }

    @Override
    public String toString() {
        return "" + projectName.get();
    }

    public int getProjectRate() {
        return ProjectRate.get();
    }

    public void setProjectRate(int ProjectRate) {
        this.ProjectRate.set(ProjectRate);
    }
    
    
    
    

    
    
    
    
    
            
            
            
    
}
