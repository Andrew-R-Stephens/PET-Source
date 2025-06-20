package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission

interface MissionRepository {

    fun getMissions(): Result<List<Mission>>

}