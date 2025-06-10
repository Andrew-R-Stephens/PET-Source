package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroupItem

data class CodexAchievementsGroupItemDto(
    @StringRes val infoText: Int
)

fun CodexAchievementsGroupItemDto.toDomain() =
    CodexAchievementsGroupItem(
        infoText = infoText
    )

fun List<CodexAchievementsGroupItemDto>.toDomain() = map {
    it.toDomain()
}