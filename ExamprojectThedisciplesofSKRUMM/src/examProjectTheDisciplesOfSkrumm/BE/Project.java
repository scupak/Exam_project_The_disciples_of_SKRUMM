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
    private TaskManager tm;
    private int timeSec;
    private String formatedTime;
    private SimpleIntegerProperty id;
    private SimpleStringProperty projectName;
    private Client client;
    private SimpleStringProperty clientName;
    private SimpleIntegerProperty ProjectRate;
    private SimpleIntegerProperty isPaid;
    private LocalDate creationDate;

    public Project(int id, String projectName, Client client, int projectrate) throws IOException 
    {
        this.id = new SimpleIntegerProperty(id);
        this.projectName = new SimpleStringProperty(projectName);
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
        this.ProjectRate = new SimpleIntegerProperty(projectrate);
        this.isPaid = new SimpleIntegerProperty(client.getIsPaid());
        this.tm = new TaskManager();
        this.timeSec = 0;
        this.formatedTime = null;
        
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

    public String getFormatedTime() {
        formatedTime = tm.convertSecToTimeString(timeSec);
        return formatedTime;
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
