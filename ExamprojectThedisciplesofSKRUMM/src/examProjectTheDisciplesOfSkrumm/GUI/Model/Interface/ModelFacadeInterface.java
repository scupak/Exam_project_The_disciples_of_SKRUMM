/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examProjectTheDisciplesOfSkrumm.GUI.Model.Interface;

import examProjectTheDisciplesOfSkrumm.BE.Interval;

/**
 *
 * @author kacpe
 */
public interface ModelFacadeInterface extends TaskModelInterface, ProjectModelInterface, ClientModelInterface, UserModelInterface
{
    public void newInterval(Interval interval);
}
