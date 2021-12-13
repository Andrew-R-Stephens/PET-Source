package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

import java.util.ArrayList;

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private static ArrayList<Ghost> ghosts = null;
    private static ArrayList<Evidence> evidence = null;

    /**
     *
     */
    public InvestigationData(Context c) {
        initEvidence(c);
        initGhosts(c);
    }

    /**
     *
     */
    public void initGhosts(Context c) {
        ghosts = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.evidence_ghost_names);
        for (int i = 0; i < ghostNames.length; i++) {
            Ghost ghost = new Ghost(i);
            ghost.setName(ghostNames[i]);
            TypedArray typedArray =
                    c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
            TypedArray nameTypedArray =
                    c.getResources().obtainTypedArray(typedArray.getResourceId(i, 0));
            typedArray.recycle();
            for (int j = 0; j < nameTypedArray.length(); j++) {
                ghost.addEvidence(nameTypedArray.getString(j));
            }
            nameTypedArray.recycle();
            ghosts.add(ghost);
        }
    }

    /**
     *
     */
    public void initEvidence(Context c) {
        evidence = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_tool_names);
        for (int i = 0; i < evidenceNames.length; i++) {
            Evidence evidence = new Evidence();
            evidence.setName(evidenceNames[i]);
            TypedArray typedArray = c.getResources().obtainTypedArray(R.array.evidence_icon_array);
            evidence.setIcon(typedArray.getResourceId(i, 0));
            typedArray.recycle();
            InvestigationData.evidence.add(evidence);
        }
    }

    /**
     * Sets the Ruling for a specified Evidence
     *
     * @param e
     * @param r
     */
    public void setEvidenceRuling(Evidence e, Evidence.Ruling r) {
        e.setRuling(r);
    }

    public static int getGhostCount() {
        return ghosts.size();
    }

    public static int getEvidenceCount() {
        return evidence.size();
    }

    public static Evidence getEvidence(int index) {
        return evidence.get(index);
    }

    public Ghost getGhost(int index) {
        return ghosts.get(index);
    }

    public ArrayList<Ghost> getGhosts() {
        return ghosts;
    }

    public ArrayList<Evidence> getEvidences() {
        return evidence;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void reset() {
        for (int i = 0; i < evidence.size(); i++) {
            evidence.get(i).setRuling(Evidence.Ruling.NEUTRAL);
        }
    }

    public void print() {
        for(Ghost g: ghosts) {
            Log.d("InvestigationData", g.name + ": [ " + g.toString() + "]");
        }
    }

    /**
     *
     */
    public static class Ghost {

        private int id = -1;
        private String name = "NA";

        private final ArrayList<Evidence> thisGhostEvidence = new ArrayList<>();

        /**
         *
         */
        public Ghost(int id) {
            setId(id);
        }

        /**
         *
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         *
         */
        public int getId() {
            return id;
        }

        /**
         *
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         */
        public String getName() {
            return name;
        }

        /**
         *
         */
        public void addEvidence(Evidence e) {
            thisGhostEvidence.add(e);
        }

        public void addEvidence(String evidence) {
            for (Evidence e : InvestigationData.evidence) {
                if (evidence.equals(e.getName())) {
                    addEvidence(e);
                    break;
                }
            }
        }

        /**
         *
         */
        public Evidence[] getEvidenceArray() {
            Evidence[] newEvidence = new Evidence[thisGhostEvidence.size()];
            for (int i = 0; i < newEvidence.length; i++) {
                newEvidence[i] = thisGhostEvidence.get(i);
            }
            return newEvidence;
        }

        /**
         * getEvidenceScore method
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence
         * Score can surpass a positive value of '3' if the ghost is a Mimic
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence list
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list
         *
         * @return numerical representation of the Ghost's Evidence score
         */
        public int getEvidenceScore() {

            int rating = 0;
            for (int i = 0; i < thisGhostEvidence.size(); i++) {
                Evidence e = thisGhostEvidence.get(i);
                if (e.getRuling() == Evidence.Ruling.POSITIVE && i < 3) {
                    rating++;
                }
                else if (e.getRuling() == Evidence.Ruling.NEGATIVE) {
                    return -5;
                }
            }

            for (int i = 0; i < evidence.size(); i++) {
                boolean isContained = false;
                for (Evidence value : thisGhostEvidence) {
                    if (evidence.get(i).getName().equals(value.getName())) {
                        isContained = true;
                    }
                }
                if (!isContained && evidence.get(i).getRuling() == Evidence.Ruling.POSITIVE) {
                    return -5;
                }
            }
            return rating;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            for(Evidence e: thisGhostEvidence) {
                s.append(e.name).append(", ");
            }

            return s.toString();
        }
    }


    /**
     * Evidence enums
     */
    public static class Evidence {

        private String name = null;
        private int icon;

        private Ruling ruling = Ruling.NEUTRAL;

        /**
         *
         */
        public enum Ruling {
            NEGATIVE, NEUTRAL, POSITIVE
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public int getIcon() {
            return icon;
        }

        /**
         * @param ruling
         */
        public void setRuling(Ruling ruling) {
            this.ruling = ruling;
        }

        /**
         * @return
         */
        public Ruling getRuling() {
            return ruling;
        }

        /**
         * @return
         */
        public String toString() {
            return ruling.name();
        }
    }

}