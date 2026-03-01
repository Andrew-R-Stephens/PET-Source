package com.tritiumgaming.feature.investigation.app.mappers.difficulty

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle

@StringRes fun DifficultyTitle.toStringResource(): Int =
    when (this) {
        DifficultyTitle.AMATEUR -> R.string.difficulty_title_amateur
        DifficultyTitle.INTERMEDIATE -> R.string.difficulty_title_intermediate
        DifficultyTitle.PROFESSIONAL -> R.string.difficulty_title_professional
        DifficultyTitle.NIGHTMARE -> R.string.difficulty_title_nightmare
        DifficultyTitle.INSANITY -> R.string.difficulty_title_insanity
        DifficultyTitle.CUSTOM -> R.string.difficulty_title_custom
        DifficultyTitle.CHALLENGE -> R.string.difficulty_title_challenge
        DifficultyTitle.APOCALYPSE_1 -> R.string.difficulty_title_apocalypse_1
        DifficultyTitle.APOCALYPSE_2 -> R.string.difficulty_title_apocalypse_2
        DifficultyTitle.APOCALYPSE_3 -> R.string.difficulty_title_apocalypse_3
    }

@StringRes fun DifficultyResponseType.toStringResource(): Int =
    when (this) {
        DifficultyResponseType.KNOWN -> R.string.difficulty_setting_response_known
        DifficultyResponseType.UNKNOWN -> R.string.difficulty_setting_response_unknown
    }
