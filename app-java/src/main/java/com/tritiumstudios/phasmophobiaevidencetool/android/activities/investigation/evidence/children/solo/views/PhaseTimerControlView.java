package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.children.solo.views;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.children.solo.data.PhaseTimerData;

/**
 * TimerPlayControl class
 *
 * @author TritiumGamingStudios
 */
public class PhaseTimerControlView {

    private final PhaseTimerData phaseTimerData;
    private PhaseTimerView timer;
    private AppCompatImageButton view;

    private int icon_play = 0, icon_pause = 0;

    /**
     * @param timer
     * @param play_pause_view
     * @param icon_play
     * @param icon_pause
     */
    public PhaseTimerControlView(
            PhaseTimerData phaseTimerData,
            PhaseTimerView timer,
            @NonNull AppCompatImageButton play_pause_view,
            int icon_play,
            int icon_pause) {
        this.phaseTimerData = phaseTimerData;

        setTimer(timer);
        setTextView(play_pause_view);

        setPlayBackgroundResource(icon_play);
        setPauseBackgroundResource(icon_pause);

        play_pause_view.setOnClickListener(v -> toggle());

        checkPaused();
    }

    /**
     * @param view
     */
    private void setTextView(AppCompatImageButton view) {
        this.view = view;
    }

    /**
     * @param timer
     */
    public void setTimer(PhaseTimerView timer) {
        this.timer = timer;
    }

    /**
     * @param icon_play
     */
    private void setPlayBackgroundResource(int icon_play) {
        this.icon_play = icon_play;
    }

    /**
     * @param icon_pause
     */
    private void setPauseBackgroundResource(int icon_pause) {
        this.icon_pause = icon_pause;
    }

    /**
     *
     */
    public void checkPaused() {
        if (timer != null && phaseTimerData.isPaused()) {
            view.setImageResource(icon_play);
        } else {
            play();
        }
    }

    /**
     *
     */
    public void pause() {
        view.setImageResource(icon_play);
        timer.pause();
    }

    /**
     *
     */
    public void play() {
        view.setImageResource(icon_pause);
        timer.play();
    }

    /**
     *
     */
    public void toggle() {
        if (phaseTimerData.isPaused())
            play();
        else
            pause();
    }

    public void reset() {
        pause();
    }


}
