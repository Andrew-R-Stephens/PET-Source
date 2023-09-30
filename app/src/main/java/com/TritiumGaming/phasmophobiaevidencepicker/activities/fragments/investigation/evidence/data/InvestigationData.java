package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.DrawableRes;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

import java.util.ArrayList;

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationData {

    private final EvidenceViewModel evidenceViewModel;

    private static ArrayList<Ghost> ghosts = null;
    private static ArrayList<Evidence> evidence = null;

    /**
     *
     */
    public InvestigationData(EvidenceViewModel evidenceViewModel, Context c) {
        this.evidenceViewModel = evidenceViewModel;

        initEvidence(c);
        initGhosts(c);
    }

    /**
     *
     */
    public void initGhosts(Context c) {
        ghosts = new ArrayList<>();
        String[] ghostNames = c.getResources().getStringArray(R.array.ghost_names);
        for (int i = 0; i < ghostNames.length; i++) {
            Ghost ghost = new Ghost(i);
            ghost.setName(ghostNames[i]);

            // Set Normal Evidence
            TypedArray typedArrayEvidence =
                    c.getResources().obtainTypedArray(R.array.ghost_evidence_arrays);
            TypedArray evidenceNameTypedArray =
                    c.getResources().obtainTypedArray(typedArrayEvidence.getResourceId(i, 0));
            typedArrayEvidence.recycle();
            for (int j = 0; j < evidenceNameTypedArray.length(); j++) {
                ghost.addEvidence(evidenceNameTypedArray.getString(j));
            }
            evidenceNameTypedArray.recycle();

            // Set Required Evidence
            TypedArray typedArrayRequiredEvidence =
                    c.getResources().obtainTypedArray(R.array.ghost_requiredevidence_arrays);
            TypedArray requiredEvidenceNameTypedArray =
                    c.getResources().obtainTypedArray(typedArrayRequiredEvidence.getResourceId(i, 0));
            typedArrayRequiredEvidence.recycle();
            for (int j = 0; j < requiredEvidenceNameTypedArray.length(); j++) {
                ghost.addNightmareEvidence(requiredEvidenceNameTypedArray.getString(j));
            }
            requiredEvidenceNameTypedArray.recycle();

            ghosts.add(ghost);
        }
    }

    /**
     *
     */
    public void initEvidence(Context c) {
        evidence = new ArrayList<>();
        String[] evidenceNames = c.getResources().getStringArray(R.array.evidence_type_names);
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
        for (Ghost g : getGhosts()) {
            g.setIsForcefullyRejected(false);
        }
    }

    public void print() {
        for(Ghost g: ghosts) {
            //Log.d("InvestigationData", g.name + ": [ " + g + "]");
        }
    }

    /**
     *
     */
    public class Ghost {

        private int id = -1;
        private String name = "NA";

        private boolean isForcefullyRejected = false;

        private final ArrayList<Evidence> thisGhostEvidence = new ArrayList<>();
        private final ArrayList<Evidence> thisGhostRequiredEvidence = new ArrayList<>();

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

        public void addNightmareEvidence(Evidence e) {
            thisGhostRequiredEvidence.add(e);
        }

        public void addNightmareEvidence(String evidence) {
            for (Evidence e : InvestigationData.evidence) {
                if (evidence.equals(e.getName())) {
                    addNightmareEvidence(e);
                    break;
                }
            }
        }

        public Evidence[] getEvidence() {
            Evidence[] t = new Evidence[thisGhostEvidence.size()];
            return thisGhostEvidence.toArray(t);
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

        public Evidence[] getRequiredEvidenceArray() {
            Evidence[] newEvidence = new Evidence[thisGhostRequiredEvidence.size()];
            for (int i = 0; i < newEvidence.length; i++) {
                newEvidence[i] = thisGhostRequiredEvidence.get(i);
            }
            return newEvidence;
        }

        public void setIsForcefullyRejected(boolean isForced) {
            isForcefullyRejected = isForced;
        }

        public boolean getIsForcefullyRejected() {
            return isForcefullyRejected;
        }

        /**
         * getEvidenceScore method
         * Determines the possibility of the ghost based on user-determined Evidence.
         * Score starts at '0'.
         * Score adds +1 if Ghost's Evidence list contains a positive Evidence.
         * Score can surpass a positive value of '3' if the ghost is a Mimic.
         * Score sets to '-5' if the Ghost has been forcefully rejected by user.
         * Score sets to '-5' if an Evidence type is positive but not found in Ghost's Evidence
         * list.
         * Score sets to '-5' if an Evidence type is negative and found in Ghost's Evidence list.
         *
         * @return numerical representation of the Ghost's Evidence score
         */
        public int getEvidenceScore() {

            if (isForcefullyRejected) {
                return -5;
            }

            boolean isNightmare =
                    evidenceViewModel.getDifficultyCarouselData().isDifficulty(3);
            boolean isInsanity =
                    evidenceViewModel.getDifficultyCarouselData().isDifficulty(4);

            int maxPosScore = isInsanity ? 1 : isNightmare ? 2 : 3;

            int posScore = 0, negScore = 0;

            for (Evidence e : evidence) {
                boolean isContained = false;
                for (Evidence eThis : thisGhostEvidence) {
                    if (e == eThis) {
                        isContained = true;
                        break;
                    }
                }
                if (!isContained) {
                    if (e.ruling == Evidence.Ruling.POSITIVE) {
                        return -5;
                    }
                }
            }

            for(int i = 0; i < thisGhostEvidence.size(); i++) {
                Evidence e = thisGhostEvidence.get(i);
                switch (e.ruling) {
                    case POSITIVE: {
                        if(i < 3) {
                            posScore++;
                        }
                        break;
                    }
                    case NEGATIVE: {
                        if(!(isNightmare || isInsanity))
                            return -6;

                        negScore ++;
                        if(i >= 3) {
                            return -7;
                        }
                        break;
                    }
                }
            }

            for(Evidence e : thisGhostRequiredEvidence) {
                if (e.ruling == Evidence.Ruling.NEGATIVE) {
                    //if (isNightmare || isInsanity)
                        return -8;
                }
            }

            if(posScore > maxPosScore)
                return -8;
            if(negScore > 3-maxPosScore)
                return -9;

            if(!(isNightmare || isInsanity)) {
                return posScore - negScore;
            }

            if(posScore == maxPosScore-(3-thisGhostEvidence.size()))
                for(Evidence e : thisGhostRequiredEvidence) {
                    if (e.ruling != Evidence.Ruling.POSITIVE) {
                        return -10;
                    }
                }

            return posScore;
        }

        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(name).append(": ");
            for(Evidence e: thisGhostEvidence) {
                s.append(e.name).append(", ");
            }
            if(thisGhostRequiredEvidence.size() > 0) {
                s.append(" / ");
            }
            for (Evidence e : thisGhostRequiredEvidence) {
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
        private @DrawableRes int icon;

        private Ruling ruling = Ruling.NEUTRAL;

        public enum Ruling {
            NEGATIVE, NEUTRAL, POSITIVE
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setIcon(@DrawableRes int icon) {
            this.icon = icon;
        }

        public @DrawableRes int getIcon() {
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

        public boolean isRuling(Ruling r) {
            return ruling == r;
        }

        /**
         * @return
         */
        public String toString() {
            return ruling.name();
        }
    }

}