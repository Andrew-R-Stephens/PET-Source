package com.tritiumgaming.data.codex.dto

import com.tritiumgaming.shared.operation.domain.codex.mappers.CodexAchievementsResources.AchievementContent
import com.tritiumgaming.shared.operation.domain.codex.model.achievements.CodexAchievementsGroupItem

data class CodexAchievementsGroupItemDto(
    val infoText: AchievementContent
)

fun CodexAchievementsGroupItemDto.toDomain() =
    CodexAchievementsGroupItem(
        infoText = infoText
    )

fun List<CodexAchievementsGroupItemDto>.toDomain() = map {
    it.toDomain()
}