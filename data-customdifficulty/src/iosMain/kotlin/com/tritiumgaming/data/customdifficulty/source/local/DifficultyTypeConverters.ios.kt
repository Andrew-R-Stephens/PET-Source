package com.tritiumgaming.data.customdifficulty.source.local

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession

actual class DifficultyTypeConverters actual constructor() {
    actual fun fromCursedPossessionList(value: List<CursedPossession>?): String? = null
    actual fun toCursedPossessionList(value: String?): List<CursedPossession>? = null
}
