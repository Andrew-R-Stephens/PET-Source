package com.TritiumGaming.phasmophobiaevidencepicker.assets.viewobjects;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * MapTrackControl class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class MapTrackControl {

    private EvidenceViewModel evidenceViewModel = null;
    private AppCompatTextView display = null;

    /**
     * MapTrackControl constructor
     *
     * TODO
     *
     * @param prev
     * @param next
     * @param map_title
     */
    public MapTrackControl(AppCompatImageButton prev, AppCompatImageButton next, AppCompatTextView map_title) {
        setDifficultyDisplay(map_title);
        setPrev(prev);
        setNext(next);
    }

    /**
     * init
     *
     * TODO
     *
     * @param evidenceViewModel
     */
    public void init(EvidenceViewModel evidenceViewModel){
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * setPrev
     *
     * TODO
     *
     * @param prev
     */
    private void setPrev(AppCompatImageButton prev){
        prev.setOnClickListener(v -> {
            if(evidenceViewModel != null) {
                int i = evidenceViewModel.getMapCurrentIndex() - 1;
                if (i < 0)
                    i = evidenceViewModel.getMapCount() - 1;
                evidenceViewModel.setMapCurrent(i);
                display.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
            }
        });
    }

    /**
     * setNext
     *
     * TODO
     *
     * @param next
     */
    private void setNext(AppCompatImageButton next){
        next.setOnClickListener(v -> {
            if(evidenceViewModel != null) {
                int i = evidenceViewModel.getMapCurrentIndex() + 1;
                if (i >= evidenceViewModel.getMapCount())
                    i = 0;
                evidenceViewModel.setMapCurrent(i);
                display.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
            }
        });
    }

    /**
     * setDifficultyDisplay
     *
     * TODO
     *
     * @param display
     */
    private void setDifficultyDisplay(AppCompatTextView display){
        this.display = display;
    }


    public void reset() {

    }

}

