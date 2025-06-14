package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.local

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.WorldMapModifier

class MapModifiersLocalDataSource(
    private val applicationContext: Context
): MapModifiersDataSource {

    override fun fetchSizeModifiers(): Result<List<WorldMapModifier>> {

        var modifiers = mutableListOf<WorldMapModifier>()

        val resources: Resources = applicationContext.resources
        val sizeModifiersTypedArray: TypedArray = resources.obtainTypedArray(R.array.maps_size_arrays)

        val nameIndex = 0
        val setupModifierIndex = 0
        val normalModifierIndex = 1

        for (i in 0 until sizeModifiersTypedArray.length()){
            val modifiersTypedArray: TypedArray =
                resources.obtainTypedArray(sizeModifiersTypedArray.getResourceId(i, 0))

            val name = modifiersTypedArray.getResourceId(nameIndex, 0)
            val setupModifier = modifiersTypedArray.getString(setupModifierIndex)?.toFloat() ?: 0f
            val normalModifier = modifiersTypedArray.getString(normalModifierIndex)?.toFloat() ?: 0f

            modifiers.add(i,
                WorldMapModifier(
                    name = name,
                    setupModifier = setupModifier,
                    normalModifier = normalModifier
                )
            )

            modifiersTypedArray.recycle()
        }

        sizeModifiersTypedArray.recycle()

        return Result.success(modifiers)
    }


}