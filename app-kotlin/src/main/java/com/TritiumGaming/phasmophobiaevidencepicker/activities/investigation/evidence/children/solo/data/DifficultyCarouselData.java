package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.data;

import android.content.Context;

import androidx.annotation.Nullable;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

public class DifficultyCarouselData {

    private final EvidenceViewModel evidenceViewModel;

    @Nullable
    private String[] difficultyNames = null;
    @Nullable
    private long[] difficultyTimes = null;

    private int difficulty = 0;

    public DifficultyCarouselData(@Nullable Context context, EvidenceViewModel evidenceViewModel) {

        this.evidenceViewModel = evidenceViewModel;

        String[] difficultyNames = null;
        String[] difficultyTimes = null;

        if (context != null) {
            difficultyNames =
                    context.getResources().getStringArray(R.array.evidence_timer_difficulty_names_array);
            difficultyTimes =
                    context.getResources().getStringArray(R.array.evidence_timer_difficulty_times_array);
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
    private void setDifficultyTimes(@Nullable String[] dt) {
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
    public void setDifficultyIndex(int difficulty) {
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
            setDifficultyIndex(state);

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
            setDifficultyIndex(state);

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setCanWarn(true);
            }

            return true;
        }

        return false;
    }

    public EvidenceViewModel getEvidenceViewModel() {
        return evidenceViewModel;
    }

    public boolean isResponseTypeKnown() {
        return getDifficultyIndex() < 2;
    }

    public void resetSanityData() {
        evidenceViewModel.getSanityData().reset();
    }

    public boolean isDifficulty(int difficultyIndex) {
        //Log.d("Diff", getDifficultyIndex() + " vs " + difficultyIndex);

        return getDifficultyIndex() == difficultyIndex;
    }

}
