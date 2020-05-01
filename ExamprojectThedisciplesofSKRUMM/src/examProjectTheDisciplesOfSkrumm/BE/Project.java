/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kacpe
 */
public class Project 
{
    private SimpleIntegerProperty id;
    private SimpleStringProperty projectName;
    private Client client;
    private SimpleStringProperty clientName;
    private SimpleIntegerProperty ProjectRate;
    private IntegerProperty isPaid;

    public Project(int id, String projectName, Client client, int projectrate, int isPaid) 
    {
        this.id = new SimpleIntegerProperty(id);
        this.projectName = new SimpleStringProperty(projectName);
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
        this.ProjectRate = new SimpleIntegerProperty(projectrate);
        this.isPaid =  new SimpleIntegerProperty(isPaid);
        
    }

    public int getId()
    {
        return id.get();
    }

    public void setId(int id)
    {
        this.id.set(id);
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

    public int getIsPaid() {
        return isPaid.get();
    }

    public void setIsPaid(int isPaid) {
        this.isPaid.set(isPaid);
    }
    
    
    
    
    

    
    
    
    
    
            
            
            
    
}
