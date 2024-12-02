package com.TritiumGaming.phasmophobiaevidencepicker.data.model.missions

import android.content.Context

class Mission(
    val contentRes: Int,
    var missionId: Int? = null
) {
    var selected: Boolean = false

    fun select(missionId: Int) {
        selected = true
        this.missionId = missionId
    }

    fun deselect() {
        selected = false
        missionId = null
    }

    fun getData(): String {
        return "$selected $missionId $contentRes"
    }

    fun getContent(context: Context): String {
        return context.getString(contentRes)
    }
}
