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

/**
 *
 * @author SKRUMM
 */
public class Interval
{

    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private LocalDate creationDate;
    private int totalTime;
    private int intervalTime;
    private Task task;
    private int isPaid;
    private final int id;

    public Interval(int id, LocalDateTime startTime, LocalDateTime stopTime, LocalDate creationDate, int intervalTime, Task task, int isPaid)
    {
        this.id = id;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.creationDate = creationDate;
        this.intervalTime = intervalTime;
        this.task = task;
        this.isPaid = isPaid;
    }

    /**
     * Sets the start time for an interval
     *
     * @param startTime
     */
    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }

    /**
     * Sets the stop time for an interval
     *
     * @param stopTime
     */
    public void setStopTime(LocalDateTime stopTime)
    {
        this.stopTime = stopTime;
    }

    /**
     * Sets the creation date for an interval
     *
     * @param creationDate
     */
    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    /**
     * Sets the time for an interval
     *
     * @param intervalTime
     */
    public void setIntervalTime(int intervalTime)
    {
        this.intervalTime = intervalTime;
    }

    /**
     * Gets the id for an interval
     *
     * @return int id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the IsPaid varible
     *
     * @return int isPaid
     */
    public int getIsPaid()
    {
        return isPaid;
    }

    /**
     * Sets the isPaid varible
     *
     * @param isPaid
     */
    public void setIsPaid(int isPaid)
    {
        this.isPaid = isPaid;
    }

    /**
     * Gets the total time for an interval
     *
     * @return int totalTime
     */
    public int getTotalTime()
    {
        return totalTime;
    }

    /**
     * Gets the intervals task
     *
     * @return task
     */
    public Task getTask()
    {
        return task;
    }

    /**
     * Gets the start time for an interval
     *
     * @return LocalTime startTime
     */
    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    /**
     * Gets the stop time for an interval
     *
     * @return LocalTime stopTime
     */
    public LocalDateTime getStopTime()
    {
        return stopTime;
    }

    /**
     * Gets the time for an interval
     *
     * @return int intervalTime
     */
    public int getIntervalTime()
    {
        return intervalTime;
    }

    /**
     * Gets the creation date for the interval
     *
     * @return LocalDate creationDate
     */
    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    /**
     * Gets a formatted creation date as a String
     *
     * @return String
     */
    public String getFormatedCreationDate()
    {

        return getCreationDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    }
    
    public String getFormatedStartTime()
    {
        return startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public String getFormatedStopTime()
    {
        return stopTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Gets the intervals time formatted to hours and minutes
     *
     * @return String
     */
    public String getFormatedIntervaltime()
    {
        int totalSec = intervalTime;
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
     * Formats the isPaid variable into a String
     *
     * @return String
     */
    public String paidOrNot()
    {
        if (isPaid == 0)
        {
            return "Not Paid";
        } else
        {
            return "Paid";
        }
    }

    @Override
    public String toString()
    {
        return creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "  |  " + startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " to " + stopTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + "  |  " + getFormatedIntervaltime() + "  |  " + paidOrNot();
    }

}
