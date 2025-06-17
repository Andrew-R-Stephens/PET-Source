package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostStrength
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.GhostPopupRecord

data class GhostDto(
    val id: String,
    val name: GhostTitle,
    val normalEvidence: List<String>,
    val strictEvidence: List<String>,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
)

fun GhostDto.toDomain() = GhostType(
    id = id,
    name = name
)

fun GhostDto.toLocalPopup() = GhostPopupRecord(
    id = id,
    name = name,
    info = this@toLocalPopup.info,
    strengthData = strengthData,
    weaknessData = weaknessData,
    huntData = huntData,
)

fun List<GhostDto>.toDomain() = map {
    it.toDomain()
}