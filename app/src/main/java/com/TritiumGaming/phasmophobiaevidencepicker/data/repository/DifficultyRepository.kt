package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.content.res.TypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.R

class DifficultyRepository(
    context: Context
) {

    companion object DifficultyConstraints {
        val MODIFIER = floatArrayOf(1.0f, 1.5f, 2.0f)

        val INSANITY_START = floatArrayOf(100f, 100f, 100f, 100f, 70f)
    }

    enum class DifficultyTitle { AMATEUR, INTERMEDIATE, PROFESSIONAL, NIGHTMARE, INSANITY }

    data class DifficultyModel(val name: Int = 0, val time: Long)

    /* List */
    var itemList = mutableListOf<DifficultyModel>()
    private fun setList(allNames: MutableList<Int>, allTimes: MutableList<Long>) {
        if (allNames.size == allTimes.size) {
            for (i in allNames.indices) {
                itemList.add(i, DifficultyModel(allNames[i], allTimes[i]))
            }
        }
    }
    val itemCount: Int
        get() = itemList.size

    init {
        val names = mutableListOf<Int>()
        val namesTypedArray: TypedArray =
            context.resources.obtainTypedArray(R.array.evidence_timer_difficulty_names_array)
        for (i in 0 until namesTypedArray.length()) {
            names.add(i, namesTypedArray.getResourceId(i, 0))
        }
        namesTypedArray.recycle()

        val times = mutableListOf<Long>()
        val timesListOut =
            context.resources.getStringArray(R.array.evidence_timer_difficulty_times_array)
        timesListOut.forEachIndexed { index, it ->
            times.add(index, it.toLong())
        }

        setList(names, times)

    }
}
