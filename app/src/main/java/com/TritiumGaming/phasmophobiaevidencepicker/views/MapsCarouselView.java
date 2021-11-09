package com.TritiumGaming.phasmophobiaevidencepicker.views;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;

/**
 * MapSelectControl class
 *
 * @author TritiumGamingStudios
 */
public class MapsCarouselView {

    private EvidenceViewModel evidenceViewModel = null;

    private AppCompatTextView mapNameView = null;

    /**
     * MapSelectControl parameterized constructor
     * @param prev
     * @param next
     * @param mapNameView
     */
    public MapsCarouselView(AppCompatImageButton prev, AppCompatImageButton next, AppCompatTextView mapNameView) {
        setDifficultyDisplay(mapNameView);
        setPrev(prev);
        setNext(next);
    }

    /**
     * init method
     * @param evidenceViewModel
     */
    public void init(EvidenceViewModel evidenceViewModel){
        this.evidenceViewModel = evidenceViewModel;
    }

    /**
     * setPrev method
     * @param prev
     */
    private void setPrev(AppCompatImageButton prev){
        prev.setOnClickListener(v -> {

            if(evidenceViewModel != null) {
                int i = evidenceViewModel.getMapCurrentIndex() - 1;
                if (i < 0)
                    i = evidenceViewModel.getMapCount() - 1;
                evidenceViewModel.setMapCurrent(i);
                mapNameView.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
            }

        });
    }

    /**
     * setNext method
     * @param next
     */
    private void setNext(AppCompatImageButton next){
        next.setOnClickListener(v -> {
            if(evidenceViewModel != null) {
                int i = evidenceViewModel.getMapCurrentIndex() + 1;
                if (i >= evidenceViewModel.getMapCount())
                    i = 0;
                evidenceViewModel.setMapCurrent(i);
                mapNameView.setText(evidenceViewModel.getMapCurrentName().split(" ")[0]);
            }
        });
    }

    /**
     * setDifficultyDisplay method
     * @param display
     */
    private void setDifficultyDisplay(AppCompatTextView display){
        this.mapNameView = display;
    }

}

