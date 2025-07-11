package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.mappers.MissionResources.MissionContent

class Mission(
    val content: MissionContent,
    var id: String
)