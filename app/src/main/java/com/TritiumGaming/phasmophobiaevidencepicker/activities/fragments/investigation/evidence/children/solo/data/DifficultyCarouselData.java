package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data;

import android.content.Context;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

public class DifficultyCarouselData {

    private final EvidenceViewModel evidenceViewModel;

    private String[] difficultyNames = null;
    private long[] difficultyTimes = null;

    private int difficulty = 0;

    public DifficultyCarouselData(EvidenceViewModel evidenceViewModel, Context c) {

        this.evidenceViewModel = evidenceViewModel;

        String[] difficultyNames = null;
        String[] difficultyTimes = null;

        if (c != null) {
            difficultyNames =
                    c.getResources().getStringArray(R.array.evidence_timer_difficulty_names_array);
            difficultyTimes =
                    c.getResources().getStringArray(R.array.evidence_timer_difficulty_times_array);
        }

        setDifficultyNames(difficultyNames);
        setDifficultyTimes(difficultyTimes);

    }

    public void setDifficultyNames(String[] difficultyNames) {
        this.difficultyNames = difficultyNames;
    }

    /**
     * setDifficultyTimes
     *
     * @param dt
     */
    private void setDifficultyTimes(String[] dt) {
        long[] temp = new long[0];
        if (dt != null) {
            temp = new long[dt.length];
            for (int i = 0; i < dt.length; i++)
                temp[i] = Long.parseLong(dt[i]);
        }
        setDifficultyTimes(temp);
    }

    public void setDifficultyTimes(long[] difficultyTimes) {
        this.difficultyTimes = difficultyTimes;
    }

    /**
     * @param difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return
     */
    public int getDifficultyIndex() {
        return difficulty;
    }

    public long getCurrentDifficultyTime() {
        return difficultyTimes[getDifficultyIndex()];
    }

    public String getCurrentDifficultyName() {
        return difficultyNames[getDifficultyIndex()];
    }

    public boolean decrementDifficulty() {
        if (evidenceViewModel != null) {

            int state = getDifficultyIndex() - 1;
            if (state < 0) {
                state = difficultyNames.length - 1;
            }
            setDifficulty(state);

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setCanWarn(true);
            }

            return true;
        }

        return false;
    }

    public boolean incrementDifficulty() {
        if (evidenceViewModel != null) {

            int state = getDifficultyIndex() + 1;
            if (state >= difficultyNames.length) {
                state = 0;
            }
            setDifficulty(state);

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setCanWarn(true);
            }

            return true;
        }

        return false;
    }

    public boolean isResponseTypeKnown() {
        return getDifficultyIndex() < 2;
    }

    public void resetSanityData() {
        evidenceViewModel.getSanityData().reset();
    }

}
