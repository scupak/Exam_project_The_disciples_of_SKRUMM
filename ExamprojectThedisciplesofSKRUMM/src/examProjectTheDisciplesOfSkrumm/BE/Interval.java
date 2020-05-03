/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

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
    private int totalTime;
    private int intervalTime;
    private Task task;
    
    public Interval(LocalTime startTime, LocalTime stopTime, int intervalTime, int totalTime, Task task)
    {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.intervalTime = intervalTime;
        this.totalTime = totalTime;
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
    
}
