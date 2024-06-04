package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels.InvestigationModel;

import java.util.ArrayList;

public class GhostModel {

    private InvestigationModel investigationData;

    private int id = -1;
    private String name = "NA";

    private boolean isForcefullyRejected = false;

    private final ArrayList<EvidenceModel> thisGhostEvidence = new ArrayList<>();
    private final ArrayList<EvidenceModel> thisGhostRequiredEvidence = new ArrayList<>();

    public GhostModel() {
        setId(0);
    }

    public GhostModel(InvestigationModel investigationData, int id) {
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

    public void addEvidence(EvidenceModel e) {
        thisGhostEvidence.add(e);
    }

    public void addEvidence(@NonNull String evidence) {
        for (EvidenceModel e : InvestigationModel.evidenceList.getList()) {
            if (evidence.equals(e.getName())) {
                addEvidence(e);
                break;
            }
        }
    }

    public void addNightmareEvidence(EvidenceModel e) {
        thisGhostRequiredEvidence.add(e);
    }

    public void addNightmareEvidence(@NonNull String evidence) {
        for (EvidenceModel e : InvestigationModel.evidenceList.getList()) {
            if (evidence.equals(e.getName())) {
                addNightmareEvidence(e);
                break;
            }
        }
    }

    @NonNull
    public EvidenceModel[] getEvidence() {
        EvidenceModel[] t = new EvidenceModel[thisGhostEvidence.size()];
        return thisGhostEvidence.toArray(t);
    }

    @NonNull
    public EvidenceModel[] getEvidenceArray() {
        EvidenceModel[] newEvidence = new EvidenceModel[thisGhostEvidence.size()];
        for (int i = 0; i < newEvidence.length; i++) {
            newEvidence[i] = thisGhostEvidence.get(i);
        }
        return newEvidence;
    }

    @NonNull
    public EvidenceModel[] getRequiredEvidenceArray() {
        EvidenceModel[] newEvidence = new EvidenceModel[thisGhostRequiredEvidence.size()];
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

        for (EvidenceModel e : InvestigationModel.evidenceList.getList()) {
            boolean isContained = false;
            for (EvidenceModel eThis : thisGhostEvidence) {
                if (e == eThis) {
                    isContained = true;
                    break;
                }
            }
            if (!isContained) {
                if (e.getRuling() == EvidenceModel.Ruling.POSITIVE) {
                    return -5;
                }
            }
        }

        for(int i = 0; i < thisGhostEvidence.size(); i++) {
            EvidenceModel e = thisGhostEvidence.get(i);
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

        for(EvidenceModel e : thisGhostRequiredEvidence) {
            if (e.getRuling() == EvidenceModel.Ruling.NEGATIVE) {
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
            for(EvidenceModel e : thisGhostRequiredEvidence) {
                if (e.getRuling() != EvidenceModel.Ruling.POSITIVE) {
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
        for(EvidenceModel e: thisGhostEvidence) {
            s.append(e.getName()).append(", ");
        }
        if(!thisGhostRequiredEvidence.isEmpty()) {
            s.append(" / ");
        }
        for (EvidenceModel e : thisGhostRequiredEvidence) {
            s.append(e.getName()).append(", ");
        }

        return s.toString();
    }
}
