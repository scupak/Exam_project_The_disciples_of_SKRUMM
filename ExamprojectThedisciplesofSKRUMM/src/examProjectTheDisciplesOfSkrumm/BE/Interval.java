/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BE;

import java.time.LocalDateTime;

/**
 *
 * @author Christina
 */
public class Interval
{
    private LocalDateTime startTime;
    private LocalDateTime stopTime;
    private int totalTime;
    private int intervalTime;
    private Task task;
    
    public Interval(LocalDateTime startTime, LocalDateTime stopTime, int intervalTime, Task task)
    {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.intervalTime = intervalTime;
        this.task = task;
    }

    public Task getTask()
    {
        return task;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public LocalDateTime getStopTime()
    {
        return stopTime;
    }

    public int getIntervalTime()
    {
        return intervalTime;
    }
    
}
