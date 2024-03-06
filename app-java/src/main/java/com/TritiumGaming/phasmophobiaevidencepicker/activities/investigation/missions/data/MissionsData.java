package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.missions.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

/**
 * ObjectivesData class
 *
 * @author TritiumGamingStudios
 */
public class MissionsData {

    private final ArrayList<Objective> objectivePool = new ArrayList<>();

    /**
     * @param context
     */
    public MissionsData(Context context) {
        String[] objectives = context.getResources().getStringArray(R.array.tasks_objectives_array);
        for (int i = 0; i < objectives.length; i++) {
            objectivePool.add(new Objective(objectives[i], i));
        }
    }

    /**
     * @param o
     * @return
     */
    public Objective getCopyOfObjective(Objective o) {
        for (Objective objective : objectivePool) {
            if (o.getContent().equals(objective.getContent())) {
                return objective;
            }
        }
        return null;
    }

    /**
     * @param selectedState
     * @return
     */
    public ArrayList<Objective> getObjectivesOfSelectedState(boolean selectedState) {
        ArrayList<Objective> temp = new ArrayList<>();
        for (Objective o : objectivePool) {
            if (o.isSelected() == selectedState) {
                temp.add(o);
            }
        }
        return temp;
    }

    /**
     * Objective class
     */
    public static class Objective {

        private int position;
        private final String content;
        private boolean isSelected = false;

        /**
         * @param content
         * @param position
         */
        public Objective(String content, int position) {
            this.content = content;
            this.position = position;
        }

        /**
         * @param i
         */
        public void setPosition(int i) {
            position = i;
        }

        /**
         * @return
         */
        public int getPosition() {
            return position;
        }

        /**
         * @param isSelected
         */
        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        /**
         * @return
         */
        private boolean isSelected() {
            return isSelected;
        }

        /**
         * @return
         */
        public String getContent() {
            return content;
        }

        /**
         * @return
         */
        @NonNull
        public String toString() {
            return content;
        }

    }
}
