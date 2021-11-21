package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import androidx.appcompat.widget.AppCompatImageButton;

/**
 * TimerPlayControl class
 *
 * @author TritiumGamingStudios
 */
public class PhaseTimerControlView {

    private PhaseTimerView timer = null;
    private AppCompatImageButton view = null;

    private int icon_play = 0, icon_pause = 0;

    /**
     * @param timer
     * @param play_pause_view
     * @param icon_play
     * @param icon_pause
     */
    public PhaseTimerControlView(
            PhaseTimerView timer,
            AppCompatImageButton play_pause_view,
            int icon_play,
            int icon_pause) {
        setTimer(timer);
        setTextView(play_pause_view);

        setPlayBackgroundResource(icon_play);
        setPauseBackgroundResource(icon_pause);

        play_pause_view.setOnClickListener(v -> toggleState());
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
        if (timer != null && timer.isPaused())
            view.setImageResource(icon_play);
    }

    /**
     *
     */
    public void setPaused() {
        view.setImageResource(icon_play);
        timer.pause();
    }

    /**
     *
     */
    public void setPlayed() {
        view.setImageResource(icon_pause);
        timer.unPause();
    }

    /**
     *
     */
    public void toggleState() {
        if (timer.isPaused())
            setPlayed();
        else
            setPaused();
    }


}
