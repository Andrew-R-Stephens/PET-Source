package com.tritiumgaming.feature.operation.app.mappers.difficulty

import androidx.annotation.FractionRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyInitialSanity
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyModifier
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTime
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.operation.domain.difficulty.mapper.DifficultyResources.Frequency

@StringRes fun DifficultyTitle.toStringResource(): Int =
    when (this) {
        DifficultyTitle.AMATEUR -> R.string.difficulty_title_amateur
        DifficultyTitle.INTERMEDIATE -> R.string.difficulty_title_intermediate
        DifficultyTitle.PROFESSIONAL -> R.string.difficulty_title_professional
        DifficultyTitle.NIGHTMARE -> R.string.difficulty_title_nightmare
        DifficultyTitle.INSANITY -> R.string.difficulty_title_insanity
    }

@FractionRes fun DifficultyTime.toIntegerResource(): Int =
    when (this) {
        DifficultyTime.AMATEUR -> R.integer.difficulty_time_amateur
        DifficultyTime.INTERMEDIATE -> R.integer.difficulty_time_intermediate
        DifficultyTime.PROFESSIONAL -> R.integer.difficulty_time_professional
        DifficultyTime.NIGHTMARE -> R.integer.difficulty_time_nightmare
        DifficultyTime.INSANITY -> R.integer.difficulty_time_insanity
    }

@FractionRes fun DifficultyModifier.toFractionResource(): Int =
    when (this) {
        DifficultyModifier.AMATEUR -> R.fraction.difficulty_modifier_amateur
        DifficultyModifier.INTERMEDIATE -> R.fraction.difficulty_modifier_intermediate
        DifficultyModifier.PROFESSIONAL -> R.fraction.difficulty_modifier_professional
        DifficultyModifier.NIGHTMARE -> R.fraction.difficulty_modifier_nightmare
        DifficultyModifier.INSANITY -> R.fraction.difficulty_modifier_insanity
    }

@FractionRes fun DifficultyInitialSanity.toFractionResource(): Int =
    when (this) {
        DifficultyInitialSanity.AMATEUR -> R.fraction.difficulty_initialSanity_amateur
        DifficultyInitialSanity.INTERMEDIATE -> R.fraction.difficulty_initialSanity_intermediate
        DifficultyInitialSanity.PROFESSIONAL -> R.fraction.difficulty_initialSanity_professional
        DifficultyInitialSanity.NIGHTMARE -> R.fraction.difficulty_initialSanity_nightmare
        DifficultyInitialSanity.INSANITY -> R.fraction.difficulty_initialSanity_insanity
    }

@StringRes fun DifficultyResponseType.toStringResource(): Int =
    when (this) {
        DifficultyResponseType.KNOWN -> R.string.difficulty_response_known
        DifficultyResponseType.UNKNOWN -> R.string.difficulty_response_unknown
    }

@StringRes fun Frequency.toStringResource(): Int =
    when (this) {
        Frequency.NONE -> R.string.difficulty_frequency_none
        Frequency.LOW -> R.string.difficulty_frequency_low
        Frequency.MEDIUM -> R.string.difficulty_frequency_medium
        Frequency.HIGH -> R.string.difficulty_frequency_high
        Frequency.VERY_HIGH -> R.string.difficulty_frequency_veryHigh
    }
