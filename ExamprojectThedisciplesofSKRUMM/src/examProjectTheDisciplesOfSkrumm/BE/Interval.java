/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Christina
 */
public class Interval
{
    private LocalTime startTime;
    private LocalTime stopTime;
    private LocalDate creationDate;
    private int totalTime;
    private int intervalTime;
    private Task task;
    
    public Interval(LocalTime startTime, LocalTime stopTime, LocalDate creationDate, int intervalTime, Task task)
    {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.creationDate = creationDate;
        this.intervalTime = intervalTime;
        this.task = task;
        
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public Task getTask()
    {
        return task;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public LocalTime getStopTime()
    {
        return stopTime;
    }

    public int getIntervalTime()
    {
        return intervalTime;
    }

    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    @Override
    public String toString() {
        return "Interval{" + "startTime=" + startTime + ", stopTime=" + stopTime + ", creationDate=" + creationDate + ", totalTime=" + totalTime + ", intervalTime=" + intervalTime + ", task=" + task + '}';
    }
    
    
    
    
}
