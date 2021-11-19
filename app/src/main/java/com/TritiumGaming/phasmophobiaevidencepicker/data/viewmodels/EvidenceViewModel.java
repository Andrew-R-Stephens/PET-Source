package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.DifficultyCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.EvidenceSanitySectionData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.PhaseTimerData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.WarnTextData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views.PhaseTimerView;
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
    private MapSizeData[] mapSizeData;

    private int[] radioButtonsChecked;
    private int mapCurrent = 0;

    // private EvidenceSanitySectionData sanitySectionData; TODO: place following content inside
    private PhaseTimerData timerData;
    private PhaseTimerView timerView; // TODO: replace with PhaseTimerData
    private WarnTextData warnTextData;
    private MapCarouselData mapCarouselData;
    private DifficultyCarouselData difficultyCarouselData;

    public void init(Context c) {

        if(!hasInvestigationData()) {
            setInvestigationData(new InvestigationData(c));
        }

        if(!hasDifficultyCarouselData()){
            difficultyCarouselData = new DifficultyCarouselData(this, c);
        }

        if(!hasSanityData()) {
            sanityData = new SanityData(this);
        }
    }

    public DifficultyCarouselData getDifficultyCarouselData() {
        return difficultyCarouselData;
    }

    public boolean hasDifficultyCarouselData() {
        return difficultyCarouselData != null;
    }

    /**
     *
     * @param investigationData
     */
    public void setInvestigationData(InvestigationData investigationData){
        this.investigationData = investigationData;
    }

    /**
     *
     * @return
     */
    public InvestigationData getInvestigationData(){
        return investigationData;
    }

    /**
     *
     * @return
     */
    public boolean hasInvestigationData(){
        return investigationData != null;
    }

    /**
     *
     * @param sanityRunnable
     */
    public void setSanityRunnable(SanityRunnable sanityRunnable) {
        this.sanityRunnable = sanityRunnable;
    }

    /**
     *
     * @return
     */
    public SanityRunnable getSanityRunnable(){
        return sanityRunnable;
    }

    /**
     *
     * @return
     */
    public boolean hasSanityRunnable(){
        return sanityRunnable != null;
    }

    /**
     *
     * @param sanityData
     */
    public void setSanityData(SanityData sanityData) {
        this.sanityData = sanityData;
    }

    /**
     *
     * @return
     */
    public SanityData getSanityData(){
        return sanityData;
    }

    /**
     *
     * @return
     */
    public boolean hasSanityData(){
        return sanityData != null;
    }

    /**
     *
     * @param allNames
     * @param allSizes
     */
    public void setMapSizeData(String[] allNames, int[] allSizes){
        if(allNames.length == allSizes.length) {
            mapSizeData = new MapSizeData[allSizes.length];
            for (int i = 0; i < allNames.length; i++)
                mapSizeData[i] = new MapSizeData(allNames[i], allSizes[i]);
        }
    }

    /**
     *
     * @return
     */
    public boolean hasMapSizeData() {
        return mapSizeData == null;
    }

    /**
     *
     * @return
     */
    public int getMapCount(){
        if(mapSizeData == null)
            return 0;
        return mapSizeData.length;
    }

    /**
     *
     * @param index
     */
    public void setMapCurrent(int index){
        this.mapCurrent = index;
    }

    /**
     *
     * @return
     */
    public int getMapCurrentIndex(){
        return mapCurrent;
    }

    /**
     *
     * @return
     */
    public String getMapCurrentName(){
        if(mapSizeData != null)
            return mapSizeData[mapCurrent].getName();
        return "???";
    }

    /**
     *
     * @return
     */
    public int getMapCurrentSize(){
        if(mapSizeData != null)
            return mapSizeData[mapCurrent].getSize();
        return 1;
    }

    /**
     *
     * @param radioButtonsChecked
     */
    public void setRadioButtonsChecked(int[] radioButtonsChecked){
        this.radioButtonsChecked = radioButtonsChecked;
    }

    /**
     *
     * @return
     */
    public int[] getRadioButtonsChecked(){
        return radioButtonsChecked;
    }

    /**
     *
     * @param timerView
     */
    public void setTimerView(PhaseTimerView timerView) {
        this.timerView = timerView;
    }

    /**
     *
     * @return
     */
    public PhaseTimerView getTimerView() {
        return timerView;
    }

    /**
     *
     * @return
     */
    public boolean hasTimer(){
        return timerView != null;
    }

    /**
     *
     * @return
     */
    public boolean isSetup(){
        return getTimerView().getTimeRemaining() > 0L;
    }

    /**
     *
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout) {
        if(sanityData != null) {
            sanityData.setFlashTimeoutMax(timeout);
            Log.d("SanityData", "is not null");
        }
        else
            Log.d("SanityData", "is null");
    }

    /**
     * reset method
     */
    public void reset() {
        timerView = null;
        investigationData.resetAll();
        radioButtonsChecked = null;
        if (sanityData != null)
            sanityData.reset();
        mapCurrent = 0;
    }

    /**
     * MapSizeData class
     */
    private static class MapSizeData {
        private final String name;
        private final int size;

        /**
         * MapSizeData parameterized constructor
         * @param name
         * @param size
         */
        public MapSizeData(String name, int size){
            this.name = name;
            this.size = size;
        }

        /**
         * getSize method
         * @return
         */
        public int getSize(){
            return size;
        }

        /**
         * getName method
         * @return
         */
        public String getName(){
            return name;
        }
    }
}

