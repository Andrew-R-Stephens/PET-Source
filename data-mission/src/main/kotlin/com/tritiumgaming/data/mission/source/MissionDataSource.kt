package com.tritiumgaming.data.mission.source

import com.tritiumgaming.data.mission.dto.MissionDto

interface MissionDataSource {

    fun get(): Result<List<MissionDto>>

}