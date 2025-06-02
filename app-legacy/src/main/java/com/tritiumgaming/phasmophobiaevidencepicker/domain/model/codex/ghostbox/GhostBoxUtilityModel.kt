package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.codex.ghostbox

import android.content.Context
import androidx.annotation.ArrayRes

/**
 * GhostSpeakData class
 *
 * @author TritiumGamingStudios
 */
class GhostBoxUtilityModel {
    fun getEntries(context: Context, @ArrayRes resourceArray: Int): IntArray {
        val typedArray = context.resources.obtainTypedArray(resourceArray)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }
}
