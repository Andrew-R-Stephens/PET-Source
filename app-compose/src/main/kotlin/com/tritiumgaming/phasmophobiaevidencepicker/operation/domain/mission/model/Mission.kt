package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource

class Mission(
    val mission: MissionContent,
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
        return "$selected $missionId $mission"
    }

    fun getContent(context: Context): String {
        return context.getString(mission.toStringResource())
    }
}
