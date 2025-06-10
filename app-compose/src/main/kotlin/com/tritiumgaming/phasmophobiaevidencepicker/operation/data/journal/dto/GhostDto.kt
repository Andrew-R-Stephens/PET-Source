package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord

data class GhostDto(
    val id: String,
    @StringRes val name: Int,
    @StringRes val info: Int,
    @StringRes val strengthData: Int,
    @StringRes val weaknessData: Int,
    @StringRes val huntData: Int,
    val normalEvidence: List<String>,
    val strictEvidence: List<String>
)

fun GhostDto.toDomain() = GhostType(
    id = id,
    name = name
)

fun GhostDto.toLocalPopup() = GhostPopupRecord(
    id = id,
    generalInfo = info,
    strengthData = strengthData,
    weaknessData = weaknessData,
    huntData = huntData
)

fun List<GhostDto>.toDomain() = map {
    it.toDomain()
}