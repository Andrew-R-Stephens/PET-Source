package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data;

public class DifficultyCarouselViewData {

    private String defaultDifficultyName = null;
    private String[] difficultyNames = null;
    private long[] difficultyTimes = null;

    public DifficultyCarouselViewData(String defaultDifficultyName, String[] difficultyNames, long[] difficultyTimes) {
        setDefaultDifficultyName(defaultDifficultyName);
        setDifficultyNames(difficultyNames);
        setDifficultyTimes(difficultyTimes);
    }

    public String getDefaultDifficultyName() {
        return defaultDifficultyName;
    }

    public void setDefaultDifficultyName(String defaultDifficultyName) {
        this.defaultDifficultyName = defaultDifficultyName;
    }

    public String[] getDifficultyNames() {
        return difficultyNames;
    }

    public boolean hasDifficultyNames() {
        return difficultyNames != null;
    }

    public void setDifficultyNames(String[] difficultyNames) {
        this.difficultyNames = difficultyNames;
    }

    public boolean hasDifficultyTimes() {
        return difficultyTimes != null;
    }

    public long[] getDifficultyTimes() {
        return difficultyTimes;
    }

    public void setDifficultyTimes(long[] difficultyTimes) {
        this.difficultyTimes = difficultyTimes;
    }



}
