package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.content.Context;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

/**
 * InvestigationData class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private static final short MAX_EVIDENCE_COUNT = 3;
    private static ArrayList<Ghost> allGhosts = null;

    /**
     * InvestigationData
     *
     * TODO
     *
     * @param c
     */
    public InvestigationData(Context c) {
        File file = new File(c.getApplicationContext().getFilesDir(), c.getResources().getString(R.string.localFile_ghostEvidencePair_name));
        Reader reader = null;
        try {
            reader = new FileReader(file);
            allGhosts = new ArrayList<>();
            JsonArray listItems = new Gson().fromJson(reader, JsonArray.class);
            for(JsonElement e: listItems) {
                Ghost ghost = new Ghost();
                ghost.setGhostName(e.getAsJsonObject().get("name").getAsString());
                for(JsonElement e2 : e.getAsJsonObject().getAsJsonArray("evidence")) {
                    for(int i = 0; i < Evidence.values().length; i++)
                        if(e2.getAsString().equalsIgnoreCase(Evidence.values()[i].name())){
                            ghost.addEvidence(Evidence.values()[i]);
                        }
                }
                allGhosts.add(ghost);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * setEvidenceRuling
     *
     * Sets the Ruling for a specified Evidence
     *
     * @param e
     * @param r
     */
    public void setEvidenceRuling(Evidence e, Evidence.Ruling r) {
        e.setRuling(r);
    }

    public static int getGhostCount() {
        return allGhosts.size();
    }

    public Ghost getGhost(int index) {
        return allGhosts.get(index);
    }
    /*
    public Ghost[] getGhostsArray() {
        return (Ghost[])allGhosts.toArray();
    }*/
    public ArrayList<Ghost> getGhostsList() {
        return allGhosts;
    }

    /**
     * resetAll
     *
     * TODO
     *
     * Resets the Ruling for each Evidence type
     */
    public void resetAll(){
        for(int i = 0; i < Evidence.values().length; i++)
            Evidence.values()[i].setRuling(Evidence.Ruling.NEUTRAL);
    }

    /**
     * Ghost enums
     *
     * @deprecated Replaced by above code
     */
        /*
    public enum Ghost {
        BANSHEE		(new Evidence[] {Evidence.DOTS, Evidence.FINGERPRINTS, Evidence.GHOST_ORBS}),
        DEMON		(new Evidence[] {Evidence.FINGERPRINTS, Evidence.FREEZING_TEMP, Evidence.GHOST_WRITING}),
        GORYO		(new Evidence[] {Evidence.DOTS, Evidence.EMF_5, Evidence.FINGERPRINTS}),
        HANTU		(new Evidence[] {Evidence.FINGERPRINTS, Evidence.FREEZING_TEMP, Evidence.GHOST_ORBS}),
        JINN		(new Evidence[] {Evidence.EMF_5, Evidence.FINGERPRINTS, Evidence.FREEZING_TEMP}),
        MYLING		(new Evidence[] {Evidence.EMF_5, Evidence.FINGERPRINTS, Evidence.GHOST_WRITING}),
        MARE		(new Evidence[] {Evidence.GHOST_ORBS, Evidence.GHOST_WRITING, Evidence.SPIRIT_BOX}),
        ONI			(new Evidence[] {Evidence.DOTS, Evidence.EMF_5, Evidence.FREEZING_TEMP}),
        PHANTOM		(new Evidence[] {Evidence.DOTS, Evidence.FINGERPRINTS, Evidence.SPIRIT_BOX}),
        POLTERGEIST	(new Evidence[] {Evidence.FINGERPRINTS, Evidence.GHOST_WRITING, Evidence.SPIRIT_BOX}),
        REVENANT	(new Evidence[] {Evidence.FREEZING_TEMP, Evidence.GHOST_ORBS, Evidence.GHOST_WRITING}),
        SHADE		(new Evidence[] {Evidence.EMF_5, Evidence.FREEZING_TEMP, Evidence.GHOST_WRITING}),
        SPIRIT		(new Evidence[] {Evidence.EMF_5, Evidence.SPIRIT_BOX, Evidence.GHOST_WRITING}),
        WRAITH		(new Evidence[] {Evidence.EMF_5, Evidence.SPIRIT_BOX, Evidence.DOTS}),
        YOKAI		(new Evidence[] {Evidence.DOTS, Evidence.GHOST_ORBS, Evidence.SPIRIT_BOX}),
        YUREI		(new Evidence[] {Evidence.DOTS, Evidence.FREEZING_TEMP, Evidence.GHOST_ORBS});

        private final Evidence[] evidence;

        /**
         * @param evidence
         */
        /*
        Ghost(Evidence[] evidence) {
            this.evidence = evidence;
        }
        */

        /*
        public Evidence[] getEvidence(){
            return evidence;
        }
        */

        /*
         * getEvidenceScore
         *
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence
         * Score cannot surpass a positive value of '3'
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence list
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list
         *
         * @return numerical representation of the Ghost's Evidence score
         */
        /*
        public int getEvidenceScore() {

            int rating = 0;
            for(Evidence e: evidence) {
                if (e.getRuling() == Evidence.Ruling.POSITIVE)
                    rating++;
                else if (e.getRuling() == Evidence.Ruling.NEGATIVE)
                    return -5;
            }

            for(int i = 0; i < Evidence.values().length; i++){
                boolean isContained = false;
                for (Evidence value : evidence)
                    if (Evidence.values()[i].name().equals(value.name()))
                        isContained = true;
                if(!isContained && Evidence.values()[i].getRuling() == Evidence.Ruling.POSITIVE)
                    return -5;
            }
            return rating;
        }
    }
    */

    /**
     * Ghost class
     *
     * TODO
     */
    public class Ghost {

        private String ghostName = "NA";
        private ArrayList<Evidence> thisGhostEvidence = new ArrayList<>(MAX_EVIDENCE_COUNT);

        /**
         * Ghost constructor
         *
         * TODO
         */
        public Ghost() {
        }

        /**
         * Ghost constructor
         *
         * TODO
         */
        public Ghost(String ghostName, Evidence e1, Evidence e2, Evidence e3){
            setGhostName(ghostName);
            addEvidence(e1);
            addEvidence(e2);
            addEvidence(e3);
        }

        /**
         * Ghost constructor
         *
         * TODO
         */
        public Ghost(String ghostName, Evidence[] e){
            setGhostName(ghostName);
            addEvidence(e);
        }

        /**
         * setGhostName
         *
         * TODO
         */
        public void setGhostName(String ghostName) {
            this.ghostName = ghostName;
        }

        /**
         * addEvidence
         *
         * TODO
         */
        public void addEvidence(Evidence e) {
            if(thisGhostEvidence.size() < MAX_EVIDENCE_COUNT)
                thisGhostEvidence.add(e);
        }

        /**
         * addEvidence
         *
         * TODO
         */
        public void addEvidence(Evidence[] pe) {
            for(Evidence e: pe)
                addEvidence(e);
        }

        /**
         * getEvidenceArray
         *
         * TODO
         */
        public Evidence[] getEvidenceArray() {
            Evidence[] newEvidence = new Evidence[thisGhostEvidence.size()];
            for(int i = 0; i < newEvidence.length; i++)
                newEvidence[i] = thisGhostEvidence.get(i);
            return newEvidence;
        }

        /**
         * getEvidenceList
         *
         * TODO
         */
        public ArrayList<Evidence> getEvidenceList() {
            return thisGhostEvidence;
        }

        /**
         * getEvidence
         *
         * TODO
         */
        public Evidence getEvidence(int index) {
            return thisGhostEvidence.get(index);
        }

        /**
         * containsEvidence
         *
         * TODO
         */
        public boolean containsEvidence(Evidence pe) {
            for(Evidence e: thisGhostEvidence)
                if(e == pe)
                    return true;
            return false;
        }

        /**
         * getEvidenceScore
         *
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence
         * Score cannot surpass a positive value of '3'
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence list
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list
         *
         * @return numerical representation of the Ghost's Evidence score
         */
        public int getEvidenceScore() {

            int rating = 0;
            for(Evidence e: thisGhostEvidence) {
                if (e.getRuling() == Evidence.Ruling.POSITIVE)
                    rating++;
                else if (e.getRuling() == Evidence.Ruling.NEGATIVE)
                    return -5;
            }

            for(int i = 0; i < Evidence.values().length; i++){
                boolean isContained = false;
                for (Evidence value : thisGhostEvidence)
                    if (Evidence.values()[i].name().equals(value.name()))
                        isContained = true;
                if(!isContained && Evidence.values()[i].getRuling() == Evidence.Ruling.POSITIVE)
                    return -5;
            }
            return rating;
        }
    }


    /**
     * Evidence enums
     *
     * TODO
     */
    public enum Evidence {

        DOTS, EMF_5, FINGERPRINTS, FREEZING_TEMP, GHOST_ORBS, GHOST_WRITING, SPIRIT_BOX;

        private Ruling ruling = Ruling.NEUTRAL;

        /**
         * Ruling constructor
         *
         * TODO
         */
        public enum Ruling {
            NEGATIVE, NEUTRAL, POSITIVE
        }

        private final int[] icons = {
                R.drawable.icon_dots, R.drawable.icon_emf5,
                R.drawable.icon_fingerprints, R.drawable.icon_freezing,
                R.drawable.icon_orbs, R.drawable.icon_writing,
                R.drawable.icon_spiritbox
        };

        /**
         * setRuling
         *
         * TODO
         *
         * @param ruling
         */
        public void setRuling(Ruling ruling) {
            this.ruling = ruling;
        }

        /**
         * getRuling
         *
         * TODO
         *
         * @return
         */
        public Ruling getRuling() {
            return ruling;
        }

        /**
         * getEvidenceIcon
         *
         * TODO
         *
         * @param e
         * @return
         */
        public int getEvidenceIcon(Evidence e){
            for(int i = 0; i < Evidence.values().length; i++)
                if(e == Evidence.values()[i])
                    return icons[i];

            return icons[0];
        }

        /**
         * toString
         *
         * TODO
         *
         * @return
         */
        public String toString() {
            return ruling.name();
        }
    }

}