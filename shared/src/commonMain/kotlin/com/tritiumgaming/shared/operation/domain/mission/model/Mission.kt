package com.tritiumgaming.shared.operation.domain.mission.model

import com.tritiumgaming.shared.operation.domain.mission.mappers.MissionResources.MissionContent

class Mission(
    val content: MissionContent,
    var id: String
)