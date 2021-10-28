package com.TritiumGaming.phasmophobiaevidencepicker.data.data;

import android.content.Context;
import android.content.res.TypedArray;
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
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private static final short MAX_EVIDENCE_COUNT = 3;
    private static ArrayList<Ghost> allGhosts = null;
    private static ArrayList<Evidence> allEvidence = null;

    /**
     *
     */
    public InvestigationData(Context c) {

        initEvidence(c);
        initGhosts(c);

        /*
        File file = new File(c.getApplicationContext().getFilesDir(), c.getResources().getString(R.string.localFile_ghostEvidencePair_name));
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        allGhosts = new ArrayList<>();
        JsonArray listItems = new Gson().fromJson(reader, JsonArray.class);
        int id = 0;
        for(JsonElement ghost: listItems) {
            Ghost newGhost = new Ghost(id);
            newGhost.setName(ghost.getAsJsonObject().get("name").getAsString());
            for(JsonElement evidence : ghost.getAsJsonObject().getAsJsonArray("evidence")) {
                for(int i = 0; i < Evidence.values().length; i++)
                    if(evidence.getAsString().equalsIgnoreCase(Evidence.values()[i].name())){
                        newGhost.addEvidence(Evidence.values()[i]);
                    }
            }
            for(JsonElement description : ghost.getAsJsonObject().getAsJsonArray("descriptions")) {
                String desc = description.getAsJsonObject().get();
                System.out.println(desc);
            }
            allGhosts.add(newGhost);
            Log.d("Gson", "Ghost added:\n" + newGhost.getName() + "\n" + newGhost.getEvidenceList().toString() + "\n" + newGhost.descriptionENGLISH);
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }

    /**
     *
     */
    public void initGhosts(Context c) {
        allGhosts = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.evidence_ghost_names);
        for(int i = 0; i < ghostNames.length; i++) {
            Ghost ghost = new Ghost(i);
            ghost.setName(ghostNames[i]);
            TypedArray typedArray = c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
            TypedArray nameTypedArray = c.getResources().obtainTypedArray(typedArray.getResourceId(i, 0));
            typedArray.recycle();
            for(int j = 0; j < nameTypedArray.length(); j++)
                ghost.addEvidence(nameTypedArray.getString(j));
            nameTypedArray.recycle();
            allGhosts.add(ghost);
        }
    }

    /**
     *
     */
    public void initEvidence(Context c) {
        allEvidence = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_tool_names);
        for(int i = 0; i < evidenceNames.length; i++) {
            Evidence evidence = new Evidence();
            evidence.setName(evidenceNames[i]);
            TypedArray typedArray = c.getResources().obtainTypedArray(R.array.evidence_icon_array);
            evidence.setIcon(typedArray.getResourceId(i, 0));
            typedArray.recycle();
            allEvidence.add(evidence);
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
        return allGhosts.size();
    }

    public static int getEvidenceCount() {
        return allEvidence.size();
    }

    public static Evidence getEvidence(int index) {
        return allEvidence.get(index);
    }

    public Ghost getGhost(int index) {
        return allGhosts.get(index);
    }

    public ArrayList<Ghost> getGhostsList() {
        return allGhosts;
    }

    /**
     * Resets the Ruling for each Evidence type
     */
    public void resetAll(){
        for(int i = 0; i < allEvidence.size(); i++)
            allEvidence.get(i).setRuling(Evidence.Ruling.NEUTRAL);
    }

    /**
     *
     */
    public static class Ghost {

        private int id = -1;
        private String name = "NA";
        private ArrayList<Evidence> thisGhostEvidence = new ArrayList<>(MAX_EVIDENCE_COUNT);

        /**
         *
         */
        public Ghost(int id) {
            setId(id);
        }

        /**
         *
         */
        public Ghost(int id, String ghostName, Evidence e1, Evidence e2, Evidence e3){
            setId(id);
            setName(ghostName);
            addEvidence(e1);
            addEvidence(e2);
            addEvidence(e3);
        }

        /**
         *
         */
        public Ghost(int id, String ghostName, Evidence[] e){
            setId(id);
            setName(ghostName);
            addEvidence(e);
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
            if(thisGhostEvidence.size() < MAX_EVIDENCE_COUNT)
                thisGhostEvidence.add(e);
        }

        /**
         *
         */
        public void addEvidence(Evidence[] pe) {
            for(Evidence e: pe)
                addEvidence(e);
        }

        public void addEvidence(String evidence) {
            for(Evidence e: allEvidence)
                if(evidence.equals(e.getName())) {
                    addEvidence(e);
                    break;
                }
        }

        /**
         *
         */
        public Evidence[] getEvidenceArray() {
            Evidence[] newEvidence = new Evidence[thisGhostEvidence.size()];
            for(int i = 0; i < newEvidence.length; i++)
                newEvidence[i] = thisGhostEvidence.get(i);
            return newEvidence;
        }

        /**
         *
         */
        public ArrayList<Evidence> getEvidenceList() {
            return thisGhostEvidence;
        }

        /**
         *
         */
        public Evidence getEvidence(int index) {
            return thisGhostEvidence.get(index);
        }

        /**
         *
         */
        public boolean containsEvidence(Evidence pe) {
            for(Evidence e: thisGhostEvidence)
                if(e == pe)
                    return true;
            return false;
        }

        /**
         * getEvidenceScore method
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence
         * Score cannot surpass a positive value of '3'
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence list
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list
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

            for(int i = 0; i < allEvidence.size(); i++){
                boolean isContained = false;
                for (Evidence value : thisGhostEvidence)
                    if (allEvidence.get(i).getName().equals(value.getName()))
                        isContained = true;
                if(!isContained && allEvidence.get(i).getRuling() == Evidence.Ruling.POSITIVE)
                    return -5;
            }

            return rating;

        }
        /*
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
         */
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

        /*
        private final int[] icons = {
                R.drawable.icon_dots, R.drawable.icon_emf5,
                R.drawable.icon_fingerprints, R.drawable.icon_freezing,
                R.drawable.icon_orbs, R.drawable.icon_writing,
                R.drawable.icon_spiritbox
        };
        */

        /**
         *
         * @param ruling
         */
        public void setRuling(Ruling ruling) {
            this.ruling = ruling;
        }

        /**
         *
         * @return
         */
        public Ruling getRuling() {
            return ruling;
        }

        /*
        public int getEvidenceIcon(Evidence e){
            for(int i = 0; i < Evidence.values().length; i++)
                if(e == Evidence.values()[i])
                    return icons[i];

            return icons[0];
        }
        */

        /**
         *
         * @return
         */
        public String toString() {
            return ruling.name();
        }
    }

    /*
    public enum Evidence {

        DOTS, EMF_5, FINGERPRINTS, FREEZING_TEMP, GHOST_ORBS, GHOST_WRITING, SPIRIT_BOX;

        private Ruling ruling = Ruling.NEUTRAL;

        public enum Ruling {
            NEGATIVE, NEUTRAL, POSITIVE
        }

        private final int[] icons = {
                R.drawable.icon_dots, R.drawable.icon_emf5,
                R.drawable.icon_fingerprints, R.drawable.icon_freezing,
                R.drawable.icon_orbs, R.drawable.icon_writing,
                R.drawable.icon_spiritbox
        };

        public void setRuling(Ruling ruling) {
            this.ruling = ruling;
        }

        public Ruling getRuling() {
            return ruling;
        }

        public int getEvidenceIcon(Evidence e){
            for(int i = 0; i < Evidence.values().length; i++)
                if(e == Evidence.values()[i])
                    return icons[i];

            return icons[0];
        }

        public String toString() {
            return ruling.name();
        }
    }
    */
}