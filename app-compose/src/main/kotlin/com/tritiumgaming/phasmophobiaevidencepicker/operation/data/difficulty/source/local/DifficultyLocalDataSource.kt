package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local

import android.content.Context
import android.content.res.TypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.model.DifficultyType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource

class DifficultyLocalDataSource(
    private val applicationContext: Context
): DifficultyDataSource {

    override fun fetchDifficulties(): List<DifficultyModel> {

        val resources = applicationContext.resources

        val difficulties = mutableListOf<DifficultyModel>()

        val difficultiesTypedArray: TypedArray =
            resources.obtainTypedArray(R.array.difficulties_array)
        val difficultiesLength = difficultiesTypedArray.length()

        val nameIndex = 0
        val startTimeIndex = 1
        val sanityModifierIndex = 2
        val startSanityIndex = 3

        for(i in 0 until DifficultyType.entries.size.coerceAtLeast(difficultiesLength)) {
            val difficultyTypedArray: TypedArray =
                resources.obtainTypedArray(difficultiesTypedArray.getResourceId(i, 0))

            val type = DifficultyType.entries[i]
            val name = difficultyTypedArray.getResourceId(nameIndex, 0)
            val startTime = difficultyTypedArray.getString(startTimeIndex)?.toLong() ?: 0L
            val sanityModifier =
                difficultyTypedArray.getString(sanityModifierIndex)?.toFloat() ?: 0f
            val startSanity = difficultyTypedArray.getString(startSanityIndex)?.toFloat() ?: 0f

            difficulties.add(i,
                DifficultyModel(type, name, startTime, sanityModifier, startSanity))

            difficultyTypedArray.recycle()
        }

        difficultiesTypedArray.recycle()

        return difficulties
    }

}