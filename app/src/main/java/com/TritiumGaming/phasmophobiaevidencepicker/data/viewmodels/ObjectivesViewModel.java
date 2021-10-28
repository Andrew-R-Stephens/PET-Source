package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.data.data.ObjectivesData;

/**
 * ObjectivesViewModel class
 *
 * @author TritiumGamingStudios
 */
public class ObjectivesViewModel extends ViewModel {

    private ObjectivesData.Objective[] objectivesSpinnerObjectives = null;
    private boolean[] isObjectiveCompleted = null;

    private String ghostName = null;

    private boolean responseState = false; // alone , group

    /*
     * Chosen spinner objectives
     */
    public void setObjectivesSpinnerObjectives(ObjectivesData.Objective[] objectiveSpinnerObjectives) {
        this.objectivesSpinnerObjectives = objectiveSpinnerObjectives;
    }

    public ObjectivesData.Objective[] getObjectivesSpinnerObjectives() {
        return objectivesSpinnerObjectives;
    }

    /*
     * Ghost name
     */
    public void setGhostName(String ghostName) {
        this.ghostName = ghostName;
    }

    public String getGhostName(){
        return ghostName;
    }

    /*
     * Response
     */
    public void setResponseState(boolean responseState){
        this.responseState = responseState;
    }

    public boolean getResponseState(){
        return responseState;
    }

    /*
     * Objective Completed Buttons
     */
    public void setObjectiveCompletion(boolean[] isObjectiveCompleted) {
        this.isObjectiveCompleted = isObjectiveCompleted;
    }

    public boolean[] getObjectiveCompletion(){
        return isObjectiveCompleted;
    }

    /*
     * Reset
     */
    public void reset() {
        objectivesSpinnerObjectives = null;
        isObjectiveCompleted = null;
        ghostName = null;
        responseState = false;
    }

}