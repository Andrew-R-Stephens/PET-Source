package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroupItem

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