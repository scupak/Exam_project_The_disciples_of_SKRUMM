/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.BLL.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Task;
import java.time.LocalTime;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author anton
 */
public interface TimerUtilInterface {

    public void run();
    
    public String getTotalTimestring();
     
    public String getintervalTimeString();
     
    public int getTotalSec();

    public void setTotalSec(int totalSec);

    public int getTotalIntervalSec();

    public void setTotalIntervalSec(int totalIntervalsec);

    public Label getTotalTimeLabel();

    public void setTotalTimeLabel(Label timeLabel);

    public boolean isIsRunning();

    public void setIsRunning(boolean isRunning);

    public Task getCurrenttask();
    
    public void setCurrenttask(Task currenttask);

    public LocalTime getStartTime();

    public void setStartTime(LocalTime startTime);

    public Label getIntervalLabel();

    public void setIntervalLabel(Label intervalLabel);
    
    
    
}
