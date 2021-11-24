package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.data.runnables.SanityRunnable;

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

    // private EvidenceSanitySectionData sanitySectionData; TODO: place following content inside
    private PhaseTimerData phaseTimerData;
    // private WarnTextData warnTextData;
    private MapCarouselData mapCarouselData;
    private DifficultyCarouselData difficultyCarouselData;

    public void init(Context c) {

        if (!hasInvestigationData()) {
            setInvestigationData(new InvestigationData(c));
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
        return radioButtonsChecked;
    }

    public void reset() {
        radioButtonsChecked = null;

        if(hasPhaseTimerData())
            phaseTimerData.reset();
        if(hasInvestigationData())
            investigationData.reset();
        if (hasSanityData())
            sanityData.reset();
        if(hasMapCarouselData())
            mapCarouselData.setMapCurrentIndex(0);
    }

}

