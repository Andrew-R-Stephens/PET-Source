package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.children.solo.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;

import com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation.evidence.children.solo.data.MapCarouselData;

/**
 * MapSelectControl class
 *
 * @author TritiumGamingStudios
 */
public class MapCarouselView {

    private final MapCarouselData mapCarouselData;

    @Nullable
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
            @NonNull AppCompatImageButton prev,
            @NonNull AppCompatImageButton next,
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
    private void setPrev(@NonNull AppCompatImageButton prev) {
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
    private void setNext(@NonNull AppCompatImageButton next) {
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

