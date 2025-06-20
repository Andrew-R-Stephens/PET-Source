package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.dto.MissionDto

interface MissionDataSource {

    fun get(): Result<List<MissionDto>>

}