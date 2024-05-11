package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.children.solo.data;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

public class DifficultyCarouselData {

    private final EvidenceViewModel evidenceViewModel;

    @NonNull
    private String[] titles = new String[0];
    @NonNull
    private long[] times = new long[0];

    public enum Difficulty { AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY }
    private int difficulty = Difficulty.AMATEUR.ordinal();

    public DifficultyCarouselData(@NonNull Context context, @NonNull EvidenceViewModel evidenceViewModel) {

        this.evidenceViewModel = evidenceViewModel;

        try {
            String[] difficultyNames = context.getResources()
                    .getStringArray(R.array.evidence_timer_difficulty_names_array);
            setTitles(difficultyNames);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        try {
            String[] difficultyTimes = context.getResources()
                    .getStringArray(R.array.evidence_timer_difficulty_times_array);
            setTimes(difficultyTimes);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setTitles(@NonNull String[] titles) {
        this.titles = titles;
    }

    private void setTimes(@NonNull String[] times) {
        long[] temp = new long[times.length];
        for (int i = 0; i < times.length; i++)
            temp[i] = Long.parseLong(times[i]);

        setDifficultyTimes(temp);
    }

    public void setDifficultyTimes(long[] difficultyTimes) {
        this.times = difficultyTimes;
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
        if(times == null) { return 0L; }

        return times[getDifficultyIndex()];
    }

    public String getCurrentDifficultyName() {
        if(titles == null) { return "NA"; }

        return titles[getDifficultyIndex()];
    }

    public boolean decrementDifficulty() {
        if (evidenceViewModel != null && titles != null) {

            int difficultyIndex = getDifficultyIndex() - 1;
            if (difficultyIndex < 0) {
                difficultyIndex = titles.length - 1;
            }
            setDifficultyIndex(difficultyIndex);

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setWarningAudioAllowed(true);
            }

            return true;
        }

        return false;
    }

    public boolean incrementDifficulty() {
        if (evidenceViewModel != null && titles != null) {

            int state = getDifficultyIndex() + 1;
            if (state >= titles.length) {
                state = 0;
            }
            setDifficultyIndex(state);

            if (evidenceViewModel.hasSanityData()) {
                evidenceViewModel.getSanityData().setWarningAudioAllowed(true);
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
        return getDifficultyIndex() == difficultyIndex;
    }

}
