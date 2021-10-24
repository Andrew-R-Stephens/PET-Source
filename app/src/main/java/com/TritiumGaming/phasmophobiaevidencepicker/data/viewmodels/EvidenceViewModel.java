package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.data.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.SanityRunnable;
import com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects.CCountDownTimer;

/**
 * EvidenceViewModel class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class EvidenceViewModel extends ViewModel {

    private InvestigationData investigationData = null;
    private int[] radioButtonsChecked = null;

    private SanityRunnable sanityRunnable = null;
    private SanityData sanityData = null;
    private boolean huntWarningAudioAllowed = false;

    private MapSizeData[] mapSizeData = null;
    private int mapCurrent = 0;

    private CCountDownTimer timer = null;
    private int difficulty = 0;

    /**
     * init
     *
     * TODO
     *
     * @param c
     */
    public void init(Context c) {

        if(!hasInvestigationData())
            setInvestigationData(new InvestigationData(c));

        if(!hasSanityData())
            sanityData = new SanityData(this);
    }

    /**
     * setInvestigationData
     *
     * TODO
     *
     * @param investigationData
     */
    public void setInvestigationData(InvestigationData investigationData){
        this.investigationData = investigationData;
    }

    /**
     * getInvestigationData
     *
     * TODO
     *
     * @return
     */
    public InvestigationData getInvestigationData(){
        return investigationData;
    }

    /**
     * hasInvestigationData
     *
     * TODO
     *
     * @return
     */
    public boolean hasInvestigationData(){
        return investigationData != null;
    }

    /**
     * setSanityRunnable
     *
     * TODO
     *
     * @param sanityRunnable
     */
    public void setSanityRunnable(SanityRunnable sanityRunnable) {
        this.sanityRunnable = sanityRunnable;
    }

    /**
     * getSanityRunnable
     *
     * TODO
     *
     * @return
     */
    public SanityRunnable getSanityRunnable(){
        return sanityRunnable;
    }

    /**
     * hasSanityRunnable
     *
     * TODO
     *
     * @return
     */
    public boolean hasSanityRunnable(){
        return sanityRunnable != null;
    }

    /**
     * setSanityData
     *
     * TODO
     *
     * @param sanityData
     */
    public void setSanityData(SanityData sanityData) {
        this.sanityData = sanityData;
    }

    /**
     * getSanityData
     *
     * TODO
     *
     * @return
     */
    public SanityData getSanityData(){
        return sanityData;
    }

    /**
     * hasSanityData
     *
     * TODO
     *
     * @return
     */
    public boolean hasSanityData(){
        return sanityData != null;
    }

    /**
     * setMapSizeData
     *
     * TODO
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
     * hasMapSizeData
     *
     * TODO
     *
     * @return
     */
    public boolean hasMapSizeData() {
        return mapSizeData == null;
    }

    /**
     * getMapCount
     *
     * TODO
     *
     * @return
     */
    public int getMapCount(){
        if(mapSizeData == null)
            return 0;
        return mapSizeData.length;
    }

    /**
     * setMapCurrent
     *
     * TODO
     *
     * @param index
     */
    public void setMapCurrent(int index){
        this.mapCurrent = index;
    }

    /**
     * getMapCurrentIndex
     *
     * TODO
     *
     * @return
     */
    public int getMapCurrentIndex(){
        return mapCurrent;
    }

    /**
     * getMapCurrentName
     *
     * TODO
     *
     * @return
     */
    public String getMapCurrentName(){
        if(mapSizeData != null)
            return mapSizeData[mapCurrent].getName();
        return "???";
    }

    /**
     * getMapCurrentSize
     *
     * TODO
     *
     * @return
     */
    public int getMapCurrentSize(){
        if(mapSizeData != null)
            return mapSizeData[mapCurrent].getSize();
        return 1;
    }

    /**
     * setRadioButtonsChecked
     *
     * TODO
     *
     * @param radioButtonsChecked
     */
    public void setRadioButtonsChecked(int[] radioButtonsChecked){
        this.radioButtonsChecked = radioButtonsChecked;
    }

    /**
     * getRadioButtonsChecked
     *
     * TODO
     *
     * @return
     */
    public int[] getRadioButtonsChecked(){
        return radioButtonsChecked;
    }

    /**
     * setTimer
     *
     * TODO
     *
     * @param timer
     */
    public void setTimer(CCountDownTimer timer) {
        this.timer = timer;
    }

    /**
     * getTimer
     *
     * TODO
     *
     * @return
     */
    public CCountDownTimer getTimer() {
        return timer;
    }

    /**
     * hasTimer
     *
     * TODO
     *
     * @return
     */
    public boolean hasTimer(){
        return timer != null;
    }

    /**
     * isSetup
     *
     * TODO
     *
     * @return
     */
    public boolean isSetup(){
        return getTimer().getTimeRemaining() > 0L;
    }

    /**
     * setDifficulty
     *
     * TODO
     *
     * @param difficulty
     */
    public void setDifficulty(int difficulty){
        this.difficulty = difficulty;
        if(hasSanityData())
            getSanityData().setCanWarn(true);
    }

    /**
     * getDifficulty
     *
     * TODO
     *
     * @return
     */
    public int getDifficulty(){
        return difficulty;
    }

    /**
     * setHuntWarningAudioAllowed
     *
     * TODO
     *
     * @param huntWarningAudioAllowed
     */
    public void setHuntWarningAudioAllowed(boolean huntWarningAudioAllowed) {
        this.huntWarningAudioAllowed = huntWarningAudioAllowed;
    }

    /**
     * isHuntWarningAudioAllowed
     *
     * TODO
     *
     * @return
     */
    public boolean isHuntWarningAudioAllowed(){
        return huntWarningAudioAllowed;
    }

    /**
     * setHuntWarningFlashTimeout
     *
     * TODO
     *
     * @param timeout
     */
    public void setHuntWarningFlashTimeout(int timeout) {
        if(sanityData != null)
            sanityData.setFlashTimeoutMax(timeout);
    }

    /**
     * reset
     *
     * TODO
     */
    public void reset() {
        timer = null;
        difficulty = 0;
        investigationData.resetAll();
        radioButtonsChecked = null;
        if (sanityData != null)
            sanityData.reset();
        mapCurrent = 0;
    }

    /**
     * MapSizeData class
     *
     * TODO
     */
    private static class MapSizeData {
        private final String name;
        private final int size;

        /**
         * MapSizeData constructor
         *
         * TODO
         *
         * @param name
         * @param size
         */
        public MapSizeData(String name, int size){
            this.name = name;
            this.size = size;
        }

        /**
         * getSize
         *
         * TODO
         *
         * @return
         */
        public int getSize(){
            return size;
        }

        /**
         * getName
         *
         * TODO
         *
         * @return
         */
        public String getName(){
            return name;
        }
    }
}

