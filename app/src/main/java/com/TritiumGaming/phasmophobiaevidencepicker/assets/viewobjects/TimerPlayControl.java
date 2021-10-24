package com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects;

import androidx.appcompat.widget.AppCompatImageButton;

/**
 * TimerPlayControl class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class TimerPlayControl {

    private CCountDownTimer timer = null;
    private AppCompatImageButton view = null;

    private int icon_play = 0, icon_pause = 0;

    /**
     * TimerPlayControl constructor
     *
     * TODO
     *
     * @param timer
     * @param play_pause_view
     * @param icon_play
     * @param icon_pause
     */
    public TimerPlayControl(CCountDownTimer timer, AppCompatImageButton play_pause_view, int icon_play, int icon_pause) {
        setTimer(timer);
        setTextView(play_pause_view);

        setPlayBackgroundResource(icon_play);
        setPauseBackgroundResource(icon_pause);

        play_pause_view.setOnClickListener(v -> toggleState());
    }

    /**
     * setTextView
     *
     * TODO
     *
     * @param view
     */
    private void setTextView(AppCompatImageButton view){
        this.view = view;
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
     * setPlayBackgroundResource
     *
     * TODO
     *
     * @param icon_play
     */
    private void setPlayBackgroundResource(int icon_play) {
        this.icon_play = icon_play;
    }

    /**
     * setPauseBackgroundResource
     *
     * TODO
     *
     * @param icon_pause
     */
    private void setPauseBackgroundResource(int icon_pause) {
        this.icon_pause = icon_pause;
    }

    /**
     * checkPaused
     *
     * TODO
     */
    public void checkPaused() {
        if(timer != null && timer.isPaused())
            view.setImageResource(icon_play);
    }

    /**
     * setPaused
     *
     * TODO
     */
    public void setPaused() {
        view.setImageResource(icon_play);
        timer.pause();
    }

    /**
     * setPlayed
     *
     * TODO
     */
    public void setPlayed() {
        view.setImageResource(icon_pause);
        timer.unPause();
    }

    /**
     * toggleState
     *
     * TODO
     */
    public void toggleState() {
        if(timer.isPaused())
            setPlayed();
        else
            setPaused();
    }


}
