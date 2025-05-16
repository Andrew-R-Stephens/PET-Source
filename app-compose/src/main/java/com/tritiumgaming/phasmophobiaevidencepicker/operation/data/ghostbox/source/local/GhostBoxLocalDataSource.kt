package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R

class GhostBoxLocalDataSource {

    @StringRes fun fetchGeneralRequests(context: Context): IntArray {
        val resources = context.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_general_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

    @StringRes fun fetchSpiritBoxRequests(context: Context): IntArray {
        val resources = context.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_spiritbox_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

    @StringRes fun fetchOuijaBoardRequests(context: Context): IntArray {
        val resources = context.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_ouijaboard_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

}