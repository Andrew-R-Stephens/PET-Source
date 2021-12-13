package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.runnables.SanityRunnable;

import java.util.Arrays;

/**
 * EvidenceViewModel class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceViewModel extends ViewModel {

    private InvestigationData investigationData;
    private SanityRunnable sanityRunnable;
    private SanityData sanityData;

    private int[] radioButtonsChecked;
    private int[] ghostOrder;

    private PhaseTimerData phaseTimerData;
    private MapCarouselData mapCarouselData;
    private DifficultyCarouselData difficultyCarouselData;

    public void init(Context c) {

        if (!hasInvestigationData()) {
            setInvestigationData(new InvestigationData(c));
            investigationData.print();
        }

        if (!hasDifficultyCarouselData()) {
            difficultyCarouselData = new DifficultyCarouselData(this, c);
        }

        if (!hasMapCarouselData()) {
            mapCarouselData = new MapCarouselData(this);
        }

        if (!hasSanityData()) {
            sanityData = new SanityData(this);
        }

        if (!hasPhaseTimerData()) {
            phaseTimerData = new PhaseTimerData(difficultyCarouselData);
        }
    }

    private boolean hasPhaseTimerData() {
        return phaseTimerData != null;
    }

    public PhaseTimerData getPhaseTimerData() {
        return phaseTimerData;
    }

    private boolean hasMapCarouselData() {
        return mapCarouselData != null;
    }

    public MapCarouselData getMapCarouselData() {
        return mapCarouselData;
    }

    public DifficultyCarouselData getDifficultyCarouselData() {
        return difficultyCarouselData;
    }

    public boolean hasDifficultyCarouselData() {
        return difficultyCarouselData != null;
    }

    /**
     * @param investigationData
     */
    public void setInvestigationData(InvestigationData investigationData) {
        this.investigationData = investigationData;
    }

    /**
     * @return
     */
    public InvestigationData getInvestigationData() {
        return investigationData;
    }

    /**
     * @return
     */
    public boolean hasInvestigationData() {
        return investigationData != null;
    }

    /**
     * @param sanityRunnable
     */
    public void setSanityRunnable(SanityRunnable sanityRunnable) {
        this.sanityRunnable = sanityRunnable;
    }

    /**
     * @return
     */
    public SanityRunnable getSanityRunnable() {
        return sanityRunnable;
    }

    /**
     * @return
     */
    public boolean hasSanityRunnable() {
        return sanityRunnable != null;
    }

    /**
     * @param sanityData
     */
    public void setSanityData(SanityData sanityData) {
        this.sanityData = sanityData;
    }

    /**
     * @return
     */
    public SanityData getSanityData() {
        return sanityData;
    }

    /**
     * @return
     */
    public boolean hasSanityData() {
        return sanityData != null;
    }

    public void createRadioButtonsChecked() {
        radioButtonsChecked = new int[InvestigationData.getEvidenceCount()];
        Arrays.fill(radioButtonsChecked, 1);
    }

    /**
     * @param radioButtonsChecked
     */
    public void setRadioButtonsChecked(int[] radioButtonsChecked) {
        this.radioButtonsChecked = radioButtonsChecked;
    }

    /**
     * @return
     */
    public int[] getRadioButtonsChecked() {
        if(radioButtonsChecked == null) {
            createRadioButtonsChecked();
        }

        return radioButtonsChecked;
    }

    public void setRadioButtonChecked(int evidenceIndex, int buttonIndex) {
        radioButtonsChecked[evidenceIndex] = buttonIndex;
    }

    public void createGhostOrder() {
        ghostOrder = new int[InvestigationData.getGhostCount()];

        for(int i = 0; i < ghostOrder.length; i++) {
            ghostOrder[i] = i;
        }
    }

    public void updateGhostOrder() {

        int[] newGhostOrder = new int[InvestigationData.getGhostCount()];

        for(int i = 0; i < newGhostOrder.length; i++) {
            newGhostOrder[i] = i;
        }

        for (int i = 0; i < newGhostOrder.length - 1; ) {

            int ratingA = investigationData.getGhost(
                    newGhostOrder[i]).getEvidenceScore();
            int ratingB = investigationData.getGhost(
                    newGhostOrder[i + 1]).getEvidenceScore();

            if (ratingA < ratingB) {
                int t = newGhostOrder[i + 1];
                newGhostOrder[i + 1] = newGhostOrder[i];
                newGhostOrder[i] = t;

                if (i > 0) {
                    i--;
                }
            } else {
                i++;
            }
        }

        ghostOrder = newGhostOrder;
    }

    public int[] getGhostOrder() {
        if(ghostOrder == null) {
            updateGhostOrder();
        }

        return ghostOrder;
    }

    public void reset() {
        createRadioButtonsChecked();
        createGhostOrder();

        if(hasPhaseTimerData()) {
            phaseTimerData.reset();
        }
        if(hasInvestigationData()) {
            investigationData.reset();
        }
        if (hasSanityData()) {
            sanityData.reset();
        }
        if(hasMapCarouselData()) {
            mapCarouselData.setMapCurrentIndex(0);
        }
    }

}

