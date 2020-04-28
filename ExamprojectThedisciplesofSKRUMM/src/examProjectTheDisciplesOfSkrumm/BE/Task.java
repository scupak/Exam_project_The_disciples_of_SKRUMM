/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author kacpe
 */
public class Task 
{
    private StringProperty title;
    private Project project;
    private IntegerProperty duration;
    private StringProperty projectName;
    private StringProperty clientName;
    private IntegerProperty isPaid;
    private LocalDateTime lastUsed;
    private LocalDate creationDate;
    private LocalTime startTime;
    private LocalTime stopTime;
    private ArrayList<Task> intervals;
            

    public Task(String title, Project project, int duration) {
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
    }
    
    public Task(String title, Project project, int duration, int isPaid, LocalDateTime lastUsed) 
    {
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid =  new SimpleIntegerProperty(isPaid);
        this.lastUsed = lastUsed;
    }
    public Task(String title, Project project, int duration, int isPaid, LocalDateTime lastUsed, LocalDate creationDate, LocalTime startTime) 
    {
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid =  new SimpleIntegerProperty(isPaid);
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        
    }
    
    public Task(String title, Project project, int duration, int isPaid, LocalDateTime lastUsed, LocalDate creationDate, LocalTime startTime, LocalTime stopTime, ArrayList<Task> intervals) 
    {
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid =  new SimpleIntegerProperty(isPaid);
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.intervals = intervals;
    }
    

    public LocalDateTime getLastUsed()
    {
        return lastUsed;
    }

    public void setLastUsed(LocalDateTime lastUsed)
    {
        this.lastUsed = lastUsed;
    }

    public int getIsPaid()
    {
        return isPaid.get();
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
    }

    public int getDuration() {
        return duration.get();
    }

    public void setDuration(int time) {
        this.duration.set(time);
    }

    public String getProjectName() {
        return projectName.get();
    }

    public String getClientName() {
        return clientName.get();
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
    }

    public ArrayList<Task> getIntervals() {
        return intervals;
    }

    public void setIntervals(ArrayList<Task> intervals) {
        this.intervals = intervals;
    }
    
    
    
    

    
    

    
    

    
    
    
    

   

  

   
    
    
    
}
