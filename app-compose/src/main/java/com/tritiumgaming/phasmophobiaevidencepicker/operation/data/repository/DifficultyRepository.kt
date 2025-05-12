package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository

import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

class DifficultyRepository(
    context: Context
) {

    /* List */
    val difficulties = mutableListOf<DifficultyModel>()

    val itemCount: Int
        get() = difficulties.size

    init {
        val resources: Resources = context.resources
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
    }

    enum class DifficultyType { AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY }

    data class DifficultyModel(
        val type: DifficultyType,
        @StringRes val name: Int = R.string.difficulty_title_default,
        val time: Long,
        val modifier: Float,
        val initialSanity: Float
    )

}
