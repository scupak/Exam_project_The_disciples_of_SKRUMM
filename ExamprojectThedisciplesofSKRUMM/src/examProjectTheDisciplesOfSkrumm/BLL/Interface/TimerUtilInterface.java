/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.time.LocalTime;
import javafx.scene.control.Label;

/**
 *
 * @author SKRUMM
 */
public interface TimerUtilInterface {

    /**
     * runs the timer utility as a separate thread
     */
    public void run();
    
    /**
     * gets the total time as a string
     * @return  the total time as string
     */
    public String getTotalTimestring();
     
    /**
     * gets the interval time as a string
     * @return the interval time as a string
     */
    public String getintervalTimeString();
     
    /**
     * gets the total amount of seconds from the timer thread
     * @return the total amount of seconds from the timer thread
     */
    public int getTotalSec();

    /**
     * sets the total amount of seconds to the given integer
     * @param totalSec 
     */
    public void setTotalSec(int totalSec);

    /**
     * gets the total amount of seconds from the interval
     * @return the total amount of seconds from the interval
     */
    public int getTotalIntervalSec();

    /**
     * sets the total amount of seconds to the given integer
     * @param totalIntervalsec 
     */
    public void setTotalIntervalSec(int totalIntervalsec);

    /**
     * gets the total time label
     * @return the total time label
     */
    public Label getTotalTimeLabel();

    /**
     * sets the total time label to the given label
     * @param timeLabel 
     */
    public void setTotalTimeLabel(Label timeLabel);

    /**
     * checks if the timer util is running
     * @return if the timer util is running (true or false)
     */
    public boolean isIsRunning();

    /**
     * sets isRunning to either true or false to tell the util that it is running.
     * @param isRunning 
     */
    public void setIsRunning(boolean isRunning);

    /**
     * gets the current task
     * @return the current task
     */
    public Task getCurrenttask();
    
    /**
     * sets the current task
     * @param currenttask 
     */
    public void setCurrenttask(Task currenttask);

    /**
     * gets the start time
     * @return the start time
     */
    public LocalTime getStartTime();

    /**
     * sets the start time
     * @param startTime 
     */
    public void setStartTime(LocalTime startTime);

    /**
     * gets the interval label
     * @return the interval label as the label type
     */
    public Label getIntervalLabel();

    /**
     * sets the interval label to the given label
     * @param intervalLabel 
     */
    public void setIntervalLabel(Label intervalLabel); 
}
