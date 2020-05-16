/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import examProjectTheDisciplesOfSkrumm.BLL.TaskManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author kacpe
 */
public class Project 
{
    private int timeSec;
    private String formatedTime;
    private SimpleIntegerProperty id;
    private SimpleStringProperty projectName;
    private Client client;
    private SimpleStringProperty clientName;
    private SimpleIntegerProperty ProjectRate;
    private SimpleIntegerProperty isPaid;
    private LocalDate creationDate;

    public Project(int id, String projectName, Client client, int projectrate)
    {
        this.id = new SimpleIntegerProperty(id);
        this.projectName = new SimpleStringProperty(projectName);
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
        this.ProjectRate = new SimpleIntegerProperty(projectrate);
        this.isPaid = new SimpleIntegerProperty(client.getIsPaid());
        this.timeSec = 0;
        this.formatedTime = null;
        this.creationDate = null;
        
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

    

    public int getProjectRate() {
        return ProjectRate.get();
    }

    public void setProjectRate(int ProjectRate) {
        this.ProjectRate.set(ProjectRate);
    }

    public int getIsPaid() {
        return isPaid.get();
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }
    
    

    public String getFormatedTime() {
         int totalSec = timeSec;
        int hour = 0;
        int min = 0; 
        int sec = 0;
        
    
         while(totalSec >= 3600){
        totalSec  = totalSec - 3600;
        hour++;
        System.out.println("added one to hours...");
        }
        
        while(totalSec >= 60){
        totalSec = totalSec - 60;
        min++;
        System.out.println("added one to min...");
        }
        
        sec = totalSec;
        System.out.println("added rest of seconds to sec...");
        
        return(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
      
    }

    public void setFormatedTime(String formatedTime) {
        this.formatedTime = formatedTime;
    }
    
    

    @Override
    public String toString() {
        return  id.get() + "  " + projectName.get() + "  " + clientName.get() + "  " + ProjectRate.get() + "  " + isPaid.get();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Project other = (Project) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
}
