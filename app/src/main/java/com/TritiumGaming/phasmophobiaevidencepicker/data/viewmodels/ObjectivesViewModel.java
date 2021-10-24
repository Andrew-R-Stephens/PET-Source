package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.data.data.ObjectivesData;

/**
 * ObjectivesViewModel class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class ObjectivesViewModel extends ViewModel {

    private ObjectivesData.Objective[] objectivesSpinnerObjectives = null;
    private boolean[] isObjectiveCompleted = null;

    private String ghostName = null;

    private boolean responseState = false; // alone , group

    /**
     *setObjectivesSpinnerObjectives
     *
     * Chosen spinner objectives
     */
    public void setObjectivesSpinnerObjectives(ObjectivesData.Objective[] objectiveSpinnerObjectives) {
        this.objectivesSpinnerObjectives = objectiveSpinnerObjectives;
    }

    /**
     * getObjectivesSpinnerObjectives
     *
     * TODO
     *
     * @return
     */
    public ObjectivesData.Objective[] getObjectivesSpinnerObjectives() {
        return objectivesSpinnerObjectives;
    }

    /**
     * setGhostName
     *
     * TODO
     *
     * @param ghostName
     */
    public void setGhostName(String ghostName) {
        this.ghostName = ghostName;
    }

    public String getGhostName(){
        return ghostName;
    }

    /**
     * setResponseState
     *
     * TODO
     */
    public void setResponseState(boolean responseState){
        this.responseState = responseState;
    }

    /**
     * getResponseState
     *
     * TODO
     *
     * @return
     */
    public boolean getResponseState(){
        return responseState;
    }

    /**
     * setObjectiveCompletion
     *
     * TODO
     *
     * Objective Completed Buttons
     */
    public void setObjectiveCompletion(boolean[] isObjectiveCompleted) {
        this.isObjectiveCompleted = isObjectiveCompleted;
    }

    /**
     * getObjectiveCompletion
     *
     * TODO
     *
     * @return
     */
    public boolean[] getObjectiveCompletion(){
        return isObjectiveCompleted;
    }

    /**
     * reset
     *
     * TODO
     */
    public void reset() {
        objectivesSpinnerObjectives = null;
        isObjectiveCompleted = null;
        ghostName = null;
        responseState = false;
    }

}