package com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.data.MapCarouselData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.children.solo.data.PhaseTimerData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.GhostOrderData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.InvestigationData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.SanityData;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.investigationtype.EvidenceList;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.investigationtype.GhostList;
import com.tritiumstudios.phasmophobiaevidencetool.activities.investigation.evidence.data.runnables.SanityRunnable;

import java.util.Arrays;

/**
 * EvidenceViewModel class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceViewModel extends ViewModel {

    private InvestigationData investigationData;
    private GhostOrderData ghostOrderData;

    private int[] radioButtonsChecked;
    private boolean[] rejectionPile;

    private SanityRunnable sanityRunnable;
    private SanityData sanityData;

    private PhaseTimerData phaseTimerData;
    private MapCarouselData mapCarouselData;
    private DifficultyCarouselData difficultyCarouselData;

    private boolean isCollapsed = false;

    public void init(Context context) {

        if (!hasInvestigationData()) {
            setInvestigationData(new InvestigationData(context, this));
        }

        if (!hasDifficultyCarouselData()) {
            difficultyCarouselData = new DifficultyCarouselData(context, this);
        }

        if (!hasMapCarouselData()) {
            mapCarouselData = new MapCarouselData();
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

    private boolean hasMapCarouselData() {
        return mapCarouselData != null;
    }

    public boolean hasDifficultyCarouselData() {
        return difficultyCarouselData != null;
    }

    public boolean hasInvestigationData() {
        return investigationData != null;
    }

    public boolean hasSanityRunnable() {
        return sanityRunnable != null;
    }

    public boolean hasSanityData() {
        return sanityData != null;
    }

    public boolean hasGhostOrderData() {
        return ghostOrderData != null;
    }

    public PhaseTimerData getPhaseTimerData() {
        return phaseTimerData;
    }

    public MapCarouselData getMapCarouselData() {
        return mapCarouselData;
    }

    public DifficultyCarouselData getDifficultyCarouselData() {
        return difficultyCarouselData;
    }

    public InvestigationData getInvestigationData() {
        return investigationData;
    }

    public SanityRunnable getSanityRunnable() {
        return sanityRunnable;
    }

    public SanityData getSanityData() {
        return sanityData;
    }

    public GhostOrderData getGhostOrderData() {
        return ghostOrderData;
    }

    public void setInvestigationData(InvestigationData investigationData) {
        this.investigationData = investigationData;
    }

    public void setSanityRunnable(SanityRunnable sanityRunnable) {
        this.sanityRunnable = sanityRunnable;
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

    public void skipSanityToPercent(int lowerBounds, int higherBounds, int newValue) {
        if ((!(getPhaseTimerData().getTimeRemaining() > lowerBounds))
                && getSanityData().getSanityActual() < higherBounds) {
            getSanityData().setProgressManually(newValue);
        }
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

