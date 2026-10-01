package com.tritiumgaming.data.mission.model

import com.tritiumgaming.data.mission.mappers.MissionResources


class Mission(
    val content: MissionResources.MissionContent,
    var id: String
)