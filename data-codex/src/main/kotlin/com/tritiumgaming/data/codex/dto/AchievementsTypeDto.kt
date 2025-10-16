package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementContent
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroupItem

data class AchievementsTypeDto(
    val infoText: AchievementContent
)

fun AchievementsTypeDto.toDomain() =
    CodexAchievementsGroupItem(
        infoText = infoText
    )

fun List<AchievementsTypeDto>.toDomain() = map {
    it.toDomain()
}