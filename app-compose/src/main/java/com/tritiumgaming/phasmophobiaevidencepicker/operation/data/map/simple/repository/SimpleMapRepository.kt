package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.maps.mapviewer.MapInteractModel

class SimpleMapRepository(
    context: Context,
    localSource: SimpleMapLocalDataSource
) {

    var modifiers: MutableList<MapSizeModel> = mutableListOf()

    var maps: MutableList<MapInteractModel> = mutableListOf()

    val mapThumbnails: MutableList<Int>
        @DrawableRes get() {
            @DrawableRes val mapThumbnails = mutableListOf<Int>()
            maps.forEachIndexed { index, it ->
                mapThumbnails.add(index, it.thumbnailImage)
            }
            return mapThumbnails
        }

    init {
        val maps = localSource.fetchMaps(context)
        val modifiers = localSource.fetchSizeModifiers(context)
    }

    data class MapSizeModel(
        @StringRes val name: Int = R.string.difficulty_title_default,
        val setupModifier: Float = 0f,
        val normalModifier: Float = 0f
    )
}