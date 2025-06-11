package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType

data class EvidenceDto(
    val id: String,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    @IntegerRes val buyCost: Int,
    val tiers: List<EvidenceTierDto> = emptyList()
)

data class EvidenceTierDto(
    @StringRes val description: Int,
    @DrawableRes val animation: Int,
    @IntegerRes val levelRequirement: Int
)

fun EvidenceDto.toEvidenceTypeDto() = EvidenceTypeDto(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceDto>.toEvidenceTypeDto() = map {
    it.toEvidenceType()
}

fun EvidenceDto.toEvidenceType() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceDto>.toEvidenceType() = map {
    it.toEvidenceType()
}
