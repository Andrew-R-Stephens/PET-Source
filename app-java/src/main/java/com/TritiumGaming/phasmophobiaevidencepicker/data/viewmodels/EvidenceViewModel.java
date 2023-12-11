package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.GhostOrderData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.EvidenceList;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.investigationtype.GhostList;
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
    private GhostOrderData ghostOrderData;

    private PhaseTimerData phaseTimerData;
    private MapCarouselData mapCarouselData;
    private DifficultyCarouselData difficultyCarouselData;

    private boolean isCollapsed = false;

    private int[] radioButtonsChecked;
    private boolean[] rejectionPile;

    public void init(Context c) {

        if (!hasInvestigationData()) {
            Log.d("ViewModel", "creating new invest data");
            setInvestigationData(new InvestigationData(this, c));
        }

        if (!hasDifficultyCarouselData()) {
            Log.d("ViewModel", "creating new diff data");
            difficultyCarouselData = new DifficultyCarouselData(this, c);
        }

        if (!hasMapCarouselData()) {
            Log.d("ViewModel", "creating new mapcar data");
            mapCarouselData = new MapCarouselData(this);
        }

        if (!hasSanityData()) {
            sanityData = new SanityData(this);
        }

        if (!hasGhostOrderData()) {
            ghostOrderData = new GhostOrderData(this);
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

    /**
     * @return
     */
    public GhostOrderData getGhostOrderData() {
        return ghostOrderData;
    }

    /**
     * @return
     */
    public boolean hasGhostOrderData() {
        return ghostOrderData != null;
    }

    public void setCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void createRadioButtonsChecked() {
        radioButtonsChecked = new int[EvidenceList.getCount()];
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

    public void createRejectionPile() {
        rejectionPile = new boolean[GhostList.getCount()];
    }

    public boolean swapStatusInRejectedPile(int index) {
        boolean[] pile = getRejectionPile();
        pile[index] = !pile[index];

        Log.d("Rejected", Arrays.toString(pile));

        return pile[index];

    }

    public void updateRejectionPile() {
        rejectionPile = new boolean[GhostList.getCount()];

        for(int i = 0; i < rejectionPile.length; i++) {
            rejectionPile[i] =
                    investigationData.getGhostList().getAt(i).getIsForcefullyRejected();
        }
    }

    public boolean[] getRejectionPile() {
        if(rejectionPile == null) {
            updateRejectionPile();
        }

        return rejectionPile;
    }

    public boolean getRejectedStatus(int index) {
        boolean[] pile = getRejectionPile();
        if(index >= 0 && index < pile.length) {
            return false;
        }

        return getRejectionPile()[index];
    }

    public void reset() {
        createRadioButtonsChecked();

        if(hasGhostOrderData()) {
            ghostOrderData.createOrder();
        }

        createRejectionPile();

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

