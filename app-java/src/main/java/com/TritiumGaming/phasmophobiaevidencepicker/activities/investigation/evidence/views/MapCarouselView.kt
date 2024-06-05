package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.views

import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.MapCarouselModel

/**
 * MapSelectControl class
 *
 * @author TritiumGamingStudios
 */
class MapCarouselView(
    private val mapCarouselData: MapCarouselModel?,
    prev: AppCompatImageButton,
    next: AppCompatImageButton,
    mapNameView: AppCompatTextView
) {
    private var mapNameView: AppCompatTextView? = null

    /**
     * MapSelectControl parameterized constructor
     *
     * @param prev
     * @param next
     * @param mapNameView
     */
    init {
        setDifficultyDisplay(mapNameView)
        setPrev(prev)
        setNext(next)
    }

    /**
     * setPrev method
     *
     * @param prev
     */
    private fun setPrev(prev: AppCompatImageButton) {
        prev.setOnClickListener {
            if (mapCarouselData != null) {
                var i = mapCarouselData.mapCurrentIndex - 1
                if (i < 0) {
                    i = mapCarouselData.mapCount - 1
                }
                setCurrentMapIndex(i)
            }
        }
    }

    /**
     * setNext method
     *
     * @param next
     */
    private fun setNext(next: AppCompatImageButton) {
        next.setOnClickListener {
            if (mapCarouselData != null) {
                var i = mapCarouselData.mapCurrentIndex + 1
                if (i >= mapCarouselData.mapCount) {
                    i = 0
                }
                setCurrentMapIndex(i)
            }
        }
    }

    private fun setCurrentMapIndex(i: Int) {
        mapCarouselData?.mapCurrentIndex = i
        mapNameView?.text =
            mapCarouselData?.mapCurrentName?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }
                ?.toTypedArray()?.get(0)
    }

    /**
     * setDifficultyDisplay method
     *
     * @param display
     */
    private fun setDifficultyDisplay(display: AppCompatTextView) {
        this.mapNameView = display
    }
}

