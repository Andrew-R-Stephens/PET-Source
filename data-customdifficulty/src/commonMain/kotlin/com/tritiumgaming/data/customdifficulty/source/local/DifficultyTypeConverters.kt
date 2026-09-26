package com.tritiumgaming.data.customdifficulty.source.local

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.CursedPossession

expect class DifficultyTypeConverters() {
    fun fromCursedPossessionList(value: List<CursedPossession>?): String?
    fun toCursedPossessionList(value: String?): List<CursedPossession>?
}
