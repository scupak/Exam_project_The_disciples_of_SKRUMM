/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import examProjectTheDisciplesOfSkrumm.BLL.TaskManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

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
    private int isPaid;
    private final int id;
    
    public Interval(int id, LocalTime startTime, LocalTime stopTime, LocalDate creationDate, int intervalTime, Task task, int isPaid)
    {
        this.id = id;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.creationDate = creationDate;
        this.intervalTime = intervalTime;
        this.task = task;
        this.isPaid = isPaid;
    }
    
    public void setStartTime(LocalTime startTime)
    {
        this.startTime = startTime;
    }

    public void setStopTime(LocalTime stopTime)
    {
        this.stopTime = stopTime;
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    public void setIntervalTime(int intervalTime)
    {
        this.intervalTime = intervalTime;
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getIsPaid()
    {
        return isPaid;
    }

    public void setIsPaid(int isPaid)
    {
        this.isPaid = isPaid;
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
    
    public String getFormatedCreationDate()
    {
        
        return getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    
    }
    
    public String getFormatedIntervaltime() {
        int totalSec = intervalTime;
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

//    @Override
//    public String toString() {
//        return "Interval{" + "startTime=" + startTime + ", stopTime=" + stopTime + ", creationDate=" + creationDate + ", totalTime=" + totalTime + ", intervalTime=" + intervalTime + ", task=" + task + '}';
//    }
    
    public String paidOrNot()
    {
        if(isPaid == 0)
        {
            return "unpaid";
        }
        else 
        {
            return "paid";
        }
    }
    
    
    
    @Override
    public String toString()
    {
        return creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "  |  " + startTime + " to " + stopTime + "  |  " + getFormatedIntervaltime() + "  |  " + paidOrNot();
    }
    
    
}
