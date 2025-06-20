package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent

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

}
