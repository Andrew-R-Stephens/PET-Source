package com.tritiumgaming.shared.data.mission.model

import com.tritiumgaming.shared.data.mission.mappers.MissionResources.MissionContent

class Mission(
    val content: MissionContent,
    var id: String
)