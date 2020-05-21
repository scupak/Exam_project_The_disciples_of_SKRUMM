/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author SKRUMM
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
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private User user;
    private ArrayList<Interval> intervals;

    public Task(int id, String title, Project project, int duration, LocalDateTime lastUsed,
            LocalDate creationDate, LocalDateTime startTime, LocalDateTime stopTime, User user,
            ArrayList<Interval> intervals)
    {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid = new SimpleIntegerProperty(project.getIsPaid());
        this.formatedDuration = new SimpleStringProperty("");
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.user = user;
        this.intervals = intervals;
    }

    public Task(int id, String title, Project project, int duration,
            LocalDateTime lastUsed, LocalDate creationDate, LocalDateTime startTime, LocalDateTime stopTime, User user)
    {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.project = project;
        this.duration = new SimpleIntegerProperty(duration);
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
        this.isPaid = new SimpleIntegerProperty(project.getIsPaid());
        this.formatedDuration = new SimpleStringProperty("");
        this.lastUsed = lastUsed;
        this.creationDate = creationDate;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.user = user;
        this.intervals = new ArrayList<>();
    }

    /**
     * Gets the id for a task
     *
     * @return int id
     */
    public int getId()
    {
        return id.get();
    }

    /**
     * Sets the id for a task
     *
     * @param id
     */
    public void setId(int id)
    {
        this.id.set(id);
    }

    /**
     * Gets the last used date from a task
     *
     * @return LocalDateTime lastUsed
     */
    public LocalDateTime getLastUsed()
    {
        return lastUsed;
    }

    /**
     * Sets the lastUsed variable for a task
     *
     * @param lastUsed
     */
    public void setLastUsed(LocalDateTime lastUsed)
    {
        this.lastUsed = lastUsed;
    }

    /**
     * Gets the isPaid for a task
     *
     * @return int isPaid
     */
    public int getIsPaid()
    {
        return isPaid.get();
    }

    /**
     * Formats the isPaid to a string
     *
     * @return String
     */
    public String getIsPaidBoolean()
    {
        if (isPaid.get() == 0)
        {
            return "Not Paid";
        } else if (isPaid.get() == 1)
        {
            return "Paid";
        } else
        {
            return "error";
        }
    }

    /**
     * Gets the title for a task
     *
     * @return
     */
    public String getTitle()
    {
        return title.get();
    }

    /**
     * Sets the title for a task
     *
     * @param title
     */
    public void setTitle(String title)
    {
        this.title.set(title);
    }

    /**
     * Gets the project for a task
     *
     * @return project
     */
    public Project getProject()
    {
        return project;
    }

    /**
     * Sets the project for a task
     *
     * @param project
     */
    public void setProject(Project project)
    {
        this.project = project;
        this.projectName = new SimpleStringProperty(project.getProjectName());
        this.clientName = new SimpleStringProperty(project.getClient().getClientName());
    }

    /**
     * Gets the duration for a task
     *
     * @return int task
     */
    public int getDuration()
    {
        return duration.get();
    }

    /**
     * Sets the duration for a task
     *
     * @param time
     */
    public void setDuration(int time)
    {
        this.duration.set(time);
    }

    /**
     * Formats the duration to a string
     *
     * @return string
     */
    public String getFormatedDuration()
    {

        int totalSec = duration.get();
        int hour = 0;
        int min = 0;
        int sec = 0;

        while (totalSec >= 3600)
        {
            totalSec = totalSec - 3600;
            hour++;
            System.out.println("added one to hours...");
        }

        while (totalSec >= 60)
        {
            totalSec = totalSec - 60;
            min++;
            System.out.println("added one to min...");
        }

        sec = totalSec;
        System.out.println("added rest of seconds to sec...");

        return (String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
    }

    /**
     * Sets the formatted duration for a task
     *
     * @param formatedDuration
     */
    public void setFormatedDuration(String formatedDuration)
    {
        this.formatedDuration.set(formatedDuration);
    }

    /**
     * Gets the formatted lastUsed
     *
     * @return string
     */
    public String getFormatedLastUsed()
    {
        return lastUsed.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    
    public String getFormatedStartTime()
    {
        return startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
    
    public String getFormatedStopTime()
    {
        return stopTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    /**
     * gets the formatted creation date
     *
     * @return string
     */
    public String getFormatedCreationDate()
    {

        return getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    }

    /**
     * Gets a tasks project name
     *
     * @return String projectName
     */
    public String getProjectName()
    {
        return projectName.get();
    }

    /**
     * Gets a tasks client name
     *
     * @return clientName
     */
    public String getClientName()
    {
        return clientName.get();
    }

    /**
     * Gets the creation date for a task
     *
     * @return LocalDate creationDate
     */
    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Sets the creation date for a task
     *
     * @param creationDate
     */
    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * Gets the start time for a task
     *
     * @return LocalTime startTime
     */
    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    /**
     * Sets the start time for a task
     *
     * @param startTime
     */
    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Gets the stop time for a task
     *
     * @return LocalTime stopTime
     */
    public LocalDateTime getStopTime()
    {
        return stopTime;
    }

    /**
     * Sets the stop time for a task
     *
     * @param stopTime
     */
    public void setStopTime(LocalDateTime stopTime)
    {
        this.stopTime = stopTime;
    }

    /**
     * Gets the email for a user associated with a task
     *
     * @return String
     */
    public String getUserEmail()
    {
        return user.getEmail();
    }

    /**
     * Sets the email for a user associated with a task
     *
     * @param userEmail
     */
    public void setUserEmail(String userEmail)
    {
        this.user.setEmail(userEmail);
    }

    /**
     * Gets a list of intervals for a task
     *
     * @return ArrayList intervals
     */
    public ArrayList<Interval> getIntervals()
    {
        return intervals;
    }

    /**
     * Sets a list of intervals for a task
     *
     * @param intervals
     */
    public void setIntervals(ArrayList<Interval> intervals)
    {
        this.intervals = intervals;
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
        final Task other = (Task) obj;
        return Objects.equals(this.id.getValue(), other.id.getValue());
    }

    /**
     * generates hashCode
     * @return hashCode
     */
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    /**
     * converts the information of the task into an easily readable string
     * @return a string 
     */
    @Override
    public String toString()
    {
        return "Task{" + "id=" + id + ", title=" + title + ", duration=" + duration + ", projectName=" + projectName + ", clientName=" + clientName + ", lastUsed=" + lastUsed + ", creationDate=" + creationDate + ", user=" + user + '}';
    }

}
