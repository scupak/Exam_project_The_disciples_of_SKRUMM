/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author SKRUMM
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

    /**
     * Gets the id for a project
     * @return int id
     */
    public int getId()
    {
        return id.get();
    }

    /**
     * Sets the id for a project
     * @param id 
     */
    public void setId(int id)
    {
        this.id.set(id);
    }

    /**
     * Gets the name of the project
     * @return String projectName
     */
    public String getProjectName() {
        return projectName.get();
    }

    /**
     * Sets the project name
     * @param projectName 
     */
    public void setProjectName(String projectName) {
        this.projectName.set(projectName);
    }

    /**
     * Gets the client for the project
     * @return client
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client for the project
     * @param client 
     */
    public void setClient(Client client) {
        this.client = client;
        this.clientName = new SimpleStringProperty(client.getClientName());
    }

    /**
     * Gets the name for the client
     * @return String clientName
     */
    public String getClientName() {
        return clientName.get();
    }

    /**
     * Gets the rate for the project
     * @return int ProjectRate
     */
    public int getProjectRate() {
        return ProjectRate.get();
    }

    /**
     * Sets the projects rate
     * @param ProjectRate 
     */
    public void setProjectRate(int ProjectRate) {
        this.ProjectRate.set(ProjectRate);
    }

    /**
     * Gets the isPaid for the project
     * @return int isPaid
     */
    public int getIsPaid() {
        return isPaid.get();
    }

    /**
     * Gets the time for a project in seconds
     * @return int timeSec
     */
    public int getTimeSec() {
        return timeSec;
    }

    /**
     * Set the time for a project in seconds
     * @param timeSec 
     */
    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    /**
     * Gets the creation date for a project
     * @return LocalDate creationDate
     */
    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Sets the creation date for a project
     * @param creationDate 
     */
    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * converts the projects timeSec to hours and minutes
     * @return String
     */
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

    /**
     * Sets the formatted time for a project
     * @param formatedTime 
     */
    public void setFormatedTime(String formatedTime) {
        this.formatedTime = formatedTime;
    }
    
    @Override
    public String toString() {
        return  id.get() + "  " + projectName.get() + "  " + clientName.get() + "  " + ProjectRate.get() + "  " + isPaid.get();
    }

    /**
     * checks wether or not an object is equal to another object
     * @param obj
     * @return true or false if it does or dosen't equal an other object
     */
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
    
    /**
     * converts the isPaid to a String
     * @return String
     */
    public String getIsPaidBoolean()
    {
        if (isPaid.get() == 0)
        {
            return "Not Paid";
        }
        else if(isPaid.get() == 1)
        {
            return "Paid";
        }
        else
        {
            return "error";
        }
    }
}
