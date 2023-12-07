package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.views;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.solo.data.MapCarouselData;

/**
 * MapSelectControl class
 *
 * @author TritiumGamingStudios
 */
public class MapCarouselView {

    private final MapCarouselData mapCarouselData;

    private AppCompatTextView mapNameView = null;

    /**
     * MapSelectControl parameterized constructor
     *
     * @param prev
     * @param next
     * @param mapNameView
     */
    public MapCarouselView(
            MapCarouselData mapsCarouselData,
            AppCompatImageButton prev,
            AppCompatImageButton next,
            AppCompatTextView mapNameView) {

        this.mapCarouselData = mapsCarouselData;

        setDifficultyDisplay(mapNameView);
        setPrev(prev);
        setNext(next);
    }

    /**
     * setPrev method
     *
     * @param prev
     */
    private void setPrev(AppCompatImageButton prev) {
        prev.setOnClickListener(v -> {

            if (mapCarouselData != null) {
                int i = mapCarouselData.getMapCurrentIndex() - 1;
                if (i < 0) {
                    i = mapCarouselData.getMapCount() - 1;
                }
                setCurrentMapIndex(i);
            }

        });
    }

    /**
     * setNext method
     *
     * @param next
     */
    private void setNext(AppCompatImageButton next) {
        next.setOnClickListener(v -> {
            if (mapCarouselData != null) {
                int i = mapCarouselData.getMapCurrentIndex() + 1;
                if (i >= mapCarouselData.getMapCount()) {
                    i = 0;
                }
                setCurrentMapIndex(i);
            }
        });
    }

    private void setCurrentMapIndex(int i) {
        mapCarouselData.setMapCurrentIndex(i);
        mapNameView.setText(mapCarouselData.getMapCurrentName().split(" ")[0]);
    }

    /**
     * setDifficultyDisplay method
     *
     * @param display
     */
    private void setDifficultyDisplay(AppCompatTextView display) {
        this.mapNameView = display;
    }

}

