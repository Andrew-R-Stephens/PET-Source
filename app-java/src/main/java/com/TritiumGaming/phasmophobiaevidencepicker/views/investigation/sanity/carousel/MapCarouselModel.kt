package com.TritiumGaming.phasmophobiaevidencepicker.views.investigation.sanity.carousel

import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.carousels.MapCarouselModel

/**
 * MapSelectControl class
 *
 * @author TritiumGamingStudios
 */
class MapCarouselModel(
    private val mapCarouselData: MapCarouselModel?,
    prev: AppCompatImageButton,
    next: AppCompatImageButton,
    mapNameView: AppCompatTextView
) {
    private var mapNameView: AppCompatTextView? = null

    init {
        setDifficultyDisplay(mapNameView)
        setPrev(prev)
        setNext(next)
    }

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
            mapCarouselData?.mapCurrentName?.value?.split(" ".toRegex())?.dropLastWhile { it.isEmpty() }
                ?.toTypedArray()?.get(0)
    }

    private fun setDifficultyDisplay(display: AppCompatTextView) {
        this.mapNameView = display
    }
}

