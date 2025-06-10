package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

data class EvidenceTypeDto(
    val id: String = "0",
    @StringRes val name: Int = 0,
    @DrawableRes val icon: Int = R.drawable.ic_ev_dots
)

fun EvidenceTypeDto.toDomain() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceTypeDto>.toDomain() = map { it.toDomain() }