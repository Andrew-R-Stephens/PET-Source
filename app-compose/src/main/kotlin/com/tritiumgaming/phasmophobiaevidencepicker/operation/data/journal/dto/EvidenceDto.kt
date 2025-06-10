package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

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

fun EvidenceDto.toLocal() = EvidenceTypeDto(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceDto>.toLocal() = map {
    it.toDomain()
}

fun EvidenceDto.toDomain() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceDto>.toDomain() = map {
    it.toDomain()
}
