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
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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
    private IntegerProperty id;
    private StringProperty title;
    private Project project;
    private IntegerProperty duration;
    private StringProperty projectName;
    private StringProperty clientName;
    private IntegerProperty isPaid;
    private StringProperty formatedDuration;
    private LocalDateTime lastUsed;
    private LocalDate creationDate;
    private LocalTime startTime;
    private LocalTime stopTime;
    private User user;
    private ArrayList<Interval> intervals;
            

   
    
    public Task(int id, String title, Project project, int duration, LocalDateTime lastUsed,
            LocalDate creationDate, LocalTime startTime, LocalTime stopTime, User user,
            ArrayList<Interval> intervals) 
    {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid =  new SimpleIntegerProperty(project.getIsPaid());
        this.formatedDuration = new SimpleStringProperty("");
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.user = user;
        this.intervals = intervals;
    }

    public Task(int id, String title, Project project, int duration,
            LocalDateTime lastUsed, LocalDate creationDate, LocalTime startTime, LocalTime stopTime, User user)
    {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid =  new SimpleIntegerProperty(project.getIsPaid());
        this.formatedDuration = new SimpleStringProperty("");
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.user = user;
        this.intervals = new ArrayList<>();
    }
    
    

    public int getId()
    {
        return id.get();
    }

    public void setId(int id)
    {
        this.id.set(id);
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
    
    public String getIsPaidBoolean()
    {
        if (isPaid.get() == 0)
        {
            return "unpaid";
        }
        else if(isPaid.get() == 1)
        {
            return "paid";
        }
        else
        {
            return "error";
        }
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

    public String getFormatedDuration() {
        long hour = TimeUnit.SECONDS.toHours(duration.get());
        long min = TimeUnit.SECONDS.toMinutes(duration.get()) - TimeUnit.HOURS.toMinutes(hour);
        Long sec = duration.get() - TimeUnit.MINUTES.toSeconds(min);
        
       return String.format("%d:%d:%d", hour, min, sec);
    }

    public void setFormatedDuration(String formatedDuration) {
        this.formatedDuration.set(formatedDuration);
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

    public String getUserEmail()
    {
        return user.getEmail();
    }

    public void setUserEmail(String userEmail)
    {
        this.user.setEmail(userEmail);
    }
   
    public ArrayList<Interval> getIntervals() {
        return intervals;
    }

    public void setIntervals(ArrayList<Interval> intervals) {
        this.intervals = intervals;
    }
    
     @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        return Objects.equals(this.id.getValue(), other.id.getValue());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString()
    {
        return "Task{" + "id=" + id + ", title=" + title + ", duration=" + duration + ", projectName=" + projectName + ", clientName=" + clientName + ", lastUsed=" + lastUsed + ", creationDate=" + creationDate + ", user=" + user + '}';
    }

   

   

   
    
    
    
    
    
    
    

    
    

    
    

    
    
    
    

   

  

   
    
    
    
}
