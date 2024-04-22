package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.data.investigationtype;

import androidx.annotation.NonNull;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.data.InvestigationData;

import java.util.ArrayList;

public class Ghost {

    private final InvestigationData investigationData;

    private int id = -1;
    private String name = "NA";

    private boolean isForcefullyRejected = false;

    private final ArrayList<Evidence> thisGhostEvidence = new ArrayList<>();
    private final ArrayList<Evidence> thisGhostRequiredEvidence = new ArrayList<>();

    public Ghost(InvestigationData investigationData, int id) {
        this.investigationData = investigationData;

        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEvidence(Evidence e) {
        thisGhostEvidence.add(e);
    }

    public void addEvidence(@NonNull String evidence) {
        for (Evidence e : InvestigationData.evidenceList.getList()) {
            if (evidence.equals(e.getName())) {
                addEvidence(e);
                break;
            }
        }
    }

    public void addNightmareEvidence(Evidence e) {
        thisGhostRequiredEvidence.add(e);
    }

    public void addNightmareEvidence(@NonNull String evidence) {
        for (Evidence e : InvestigationData.evidenceList.getList()) {
            if (evidence.equals(e.getName())) {
                addNightmareEvidence(e);
                break;
            }
        }
    }

    @NonNull
    public Evidence[] getEvidence() {
        Evidence[] t = new Evidence[thisGhostEvidence.size()];
        return thisGhostEvidence.toArray(t);
    }

    @NonNull
    public Evidence[] getEvidenceArray() {
        Evidence[] newEvidence = new Evidence[thisGhostEvidence.size()];
        for (int i = 0; i < newEvidence.length; i++) {
            newEvidence[i] = thisGhostEvidence.get(i);
        }
        return newEvidence;
    }

    @NonNull
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
                investigationData.getEvidenceViewModel()
                        .getDifficultyCarouselData().isDifficulty(3);
        boolean isInsanity =
                investigationData.getEvidenceViewModel()
                        .getDifficultyCarouselData().isDifficulty(4);

        int maxPosScore = isInsanity ? 1 : isNightmare ? 2 : 3;

        int posScore = 0, negScore = 0;

        for (Evidence e : InvestigationData.evidenceList.getList()) {
            boolean isContained = false;
            for (Evidence eThis : thisGhostEvidence) {
                if (e == eThis) {
                    isContained = true;
                    break;
                }
            }
            if (!isContained) {
                if (e.getRuling() == Evidence.Ruling.POSITIVE) {
                    return -5;
                }
            }
        }

        for(int i = 0; i < thisGhostEvidence.size(); i++) {
            Evidence e = thisGhostEvidence.get(i);
            switch (e.getRuling()) {
                case POSITIVE -> {
                    if (i < 3) {
                        posScore++;
                    }
                }
                case NEGATIVE -> {
                    if (!(isNightmare || isInsanity))
                        return -6;

                    negScore++;
                    if (i >= 3) {
                        return -7;
                    }
                }
            }
        }

        for(Evidence e : thisGhostRequiredEvidence) {
            if (e.getRuling() == Evidence.Ruling.NEGATIVE) {
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
                if (e.getRuling() != Evidence.Ruling.POSITIVE) {
                    return -10;
                }
            }

        return posScore;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(name).append(": ");
        for(Evidence e: thisGhostEvidence) {
            s.append(e.getName()).append(", ");
        }
        if(thisGhostRequiredEvidence.size() > 0) {
            s.append(" / ");
        }
        for (Evidence e : thisGhostRequiredEvidence) {
            s.append(e.getName()).append(", ");
        }

        return s.toString();
    }
}
