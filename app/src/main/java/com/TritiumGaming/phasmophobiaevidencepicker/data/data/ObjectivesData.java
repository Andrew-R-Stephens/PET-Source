package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import java.util.ArrayList;

/**
 * ObjectivesData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class ObjectivesData {

    private final ArrayList<Objective> objectivePool = new ArrayList<>();

    /**
     * ObjectivesData
     *
     * TODO
     *
     * @param context
     */
    public ObjectivesData(Context context) {
        String[] objectives = context.getResources().getStringArray(R.array.tasks_objectives_array);
        for (int i = 0; i < objectives.length; i++) {
            objectivePool.add(new Objective(objectives[i], i));
        }
    }

    /**
     * getCopyOfObjective
     *
     * TODO
     *
     * @param o
     * @return
     */
    public Objective getCopyOfObjective(Objective o){
        for(Objective objective: objectivePool)
            if(o.getContent().equals(objective.getContent()))
                return objective;
        return null;
    }

    /**
     * getObjectivesOfSelectedState
     *
     * TODO
     *
     * @param selectedState
     * @return
     */
    public ArrayList<Objective> getObjectivesOfSelectedState(boolean selectedState) {
        ArrayList<Objective> temp = new ArrayList<>();
        for(Objective o : objectivePool)
            if(o.isSelected() == selectedState)
                temp.add(o);
        return temp;
    }

    /**
     * Objective class
     *
     * TODO
     */
    public static class Objective {

        private int position;
        private final String content;
        private boolean isSelected = false;

        /**
         * Objective
         *
         * TODO
         *
         * @param content
         * @param position
         */
        public Objective(String content, int position){
            this.content = content;
            this.position = position;
        }

        /**
         * setPosition
         *
         * TODO
         *
         * @param i
         */
        public void setPosition(int i){
            position = i;
        }

        /**
         * getPosition
         *
         * TODO
         *
         * @return
         */
        public int getPosition(){
            return position;
        }

        /**
         * setSelected
         *
         * TODO
         *
         * @param isSelected
         */
        public void setSelected(boolean isSelected){
            this.isSelected= isSelected;
        }

        /**
         * isSelected
         *
         * TODO
         *
         * @return
         */
        private boolean isSelected(){
            return isSelected;
        }

        /**
         * getContent
         *
         * TODO
         *
         * @return
         */
        public String getContent(){
            return content;
        }

        /**
         * toString
         *
         * TODO
         *
         * @return
         */
        @NonNull
        public String toString(){
            return content;
        }

    }
}
