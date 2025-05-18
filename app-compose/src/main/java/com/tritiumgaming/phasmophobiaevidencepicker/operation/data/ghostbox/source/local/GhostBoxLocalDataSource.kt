package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostbox.source.local

import android.content.Context
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostbox.source.GhostBoxDataSource

class GhostBoxLocalDataSource(
    private val applicationContext: Context
): GhostBoxDataSource {

    @StringRes
    override fun fetchGeneralRequests(): IntArray {
        val resources = applicationContext.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_general_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

    @StringRes
    override fun fetchSpiritBoxRequests(): IntArray {
        val resources = applicationContext.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_spiritbox_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

    @StringRes
    override fun fetchOuijaBoardRequests(): IntArray {
        val resources = applicationContext.resources
        val typedArray = resources.obtainTypedArray(R.array.ghostspeaktool_ouijaboard_array)
        val spiritBoxEntries = IntArray(typedArray.length())
        for (i in spiritBoxEntries.indices) {
            spiritBoxEntries[i] = typedArray.getResourceId(i, 0)
        }
        typedArray.recycle()

        return spiritBoxEntries
    }

}