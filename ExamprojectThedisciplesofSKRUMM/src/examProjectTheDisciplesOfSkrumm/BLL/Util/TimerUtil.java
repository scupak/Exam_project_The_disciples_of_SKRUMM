/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Util;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author SKRUMM
 */
public class TimerUtil implements Runnable
{
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private int intervalsec = 0;
    private int intervalmin = 0; 
    private int intervalhour = 0; 
    private String totalTimeString;
    private String intervalTimeString;
    private boolean isRunning = true;
    private Label totaltimeLabel;
    private Label intervalLabel;
    private int totalSec = 0;
    private int totalIntervalsec = 0;
    private Task currenttask;
    private LocalDateTime startTime;

    /**
     * converts the given seconds to actual time format, this is the class' constructor
     * @param intervalLabel
     * @param totaltimeLabel
     * @param totalSec
     * @param task
     * @param startTime 
     */
    public TimerUtil(Label intervalLabel,Label totaltimeLabel, int totalSec, Task task, LocalDateTime startTime) {
        this.totalSec = totalSec;
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
        
        totalTimeString = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        System.out.println(totalTimeString);
        
        //while totalSec is over 3600, totalSec minus 3600, and add 1 to hour until it isnt anymore
        //when done, while totalSec is over 60, totalSec minus 60 and add 1 to min until it isnt anymore
        //add rest of seconds to sec
        this.totaltimeLabel = totaltimeLabel;
        this.intervalLabel = intervalLabel;
        this.currenttask = task;
        this.startTime = startTime;
    }
    

    /**
     * runs the timer utility as a separate thread
     */
    @Override
    public void run()
    {
        while (!Thread.interrupted()) {
                if (isRunning) {

                    try {
                        Thread.sleep(1000);

                        sec++;
                        intervalsec++;
                        totalSec++;
                        totalIntervalsec++;

                        if (sec >= 60) {
                            min++;
                            intervalmin++;
                            intervalsec = 0;
                            sec = 0;
                        }

                        if (min >= 60) {
                            hour++;
                            intervalhour++;
                            min = 0;
                            intervalmin = 0;
                        }

                        totalTimeString = String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
                        intervalTimeString = String.format("%02d", intervalhour) + ":" + String.format("%02d", intervalmin) + ":" + String.format("%02d", intervalsec);
                        System.out.println("totalTimeString" + "    " +totalTimeString);
                        System.out.println("intervalTimeString" + "    " +intervalTimeString);
                        System.out.println("totalSec" + "    " + totalSec);
                        System.out.println("intervalsec" + "    " + intervalsec);
                        System.out.println(currenttask);
                        
                            if(totaltimeLabel != null){
                            Platform.runLater (() -> 
                            {   
                             totaltimeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                            });
                        }
                            if(totaltimeLabel == null){
                            System.out.println("Nonexistant totaltimeLabel is nonexistant, putting relevant data into time String. This is a Null Pointer");
                            }  
                            System.err.println(intervalLabel);
                            
                            if(intervalLabel != null){
                            Platform.runLater (() -> 
                            {   
                             intervalLabel.setText(String.format("%02d", intervalhour) + ":" + String.format("%02d", intervalmin) + ":" + String.format("%02d", intervalsec));
                            });
                        }
                            if( intervalLabel == null){
                            System.out.println("Nonexistant intervalLabel is nonexistant, putting relevant data into time String. This is a Null Pointer");
                            } 
                    }
                    catch (InterruptedException e){
                    }

                }
                else{
                    break;
                }

            }
        
        System.err.println("Interrupted" + Thread.currentThread().getName());
    }
    
    /**
     * gets the total time as a string
     * @return  the total time as string
     */
     public String getTotalTimestring(){
    return  totalTimeString;
    }
         
     /**
     * gets the interval time as a string
     * @return the interval time as a string
     */
     public String getintervalTimeString(){
     
         return intervalTimeString;
     }
    
    /**
     * gets the total amount of seconds from the timer thread
     * @return the total amount of seconds from the timer thread
     */
    public int getTotalSec(){
    return totalSec;
    }

    /**
     * sets the total amount of seconds to the given integer
     * @param totalSec 
     */
    public void setTotalSec(int totalSec) {
        this.totalSec = totalSec;
    }

    /**
     * gets the total amount of seconds from the interval
     * @return the total amount of seconds from the interval
     */
    public int getTotalIntervalSec() {
        return totalIntervalsec;
    }

    /**
     * sets the total amount of seconds to the given integer
     * @param totalIntervalsec 
     */
    public void setTotalIntervalSec(int totalIntervalsec) {
        this.totalIntervalsec = totalIntervalsec;
    }

    /**
     * gets the total time label
     * @return the total time label
     */
    public Label getTotalTimeLabel() {
        return totaltimeLabel;
    }

    /**
     * sets the total time label to the given label
     * @param timeLabel 
     */
    public void setTotalTimeLabel(Label timeLabel) {
        this.totaltimeLabel = timeLabel;
    }

    /**
     * checks if the timer util is running
     * @return if the timer util is running (true or false)
     */
    public boolean isIsRunning() {
        return isRunning;
    }

    /**
     * sets isRunning to either true or false to tell the util that it is running.
     * @param isRunning 
     */
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * gets the current task
     * @return the current task
     */
    public Task getCurrenttask() {
        return currenttask;
    }

    /**
     * sets the current task
     * @param currenttask 
     */
    public void setCurrenttask(Task currenttask) {
        this.currenttask = currenttask;
    }

    /**
     * gets the start time
     * @return the start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * sets the start time
     * @param startTime 
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * gets the interval label
     * @return the interval label as the label type
     */
    public Label getIntervalLabel() {
        return intervalLabel;
    }

    /**
     * sets the interval label to the given label
     * @param intervalLabel 
     */
    public void setIntervalLabel(Label intervalLabel) {
        this.intervalLabel = intervalLabel;
    }

    
    public static void main(String[] args){
        int totSec = 123456;
    }
    
}
