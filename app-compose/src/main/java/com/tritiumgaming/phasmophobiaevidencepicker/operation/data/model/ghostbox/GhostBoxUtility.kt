package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.ghostbox

import android.content.Context
import androidx.annotation.ArrayRes

/**
 * GhostSpeakData class
 *
 * @author TritiumGamingStudios
 */
class GhostBoxUtility {
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
