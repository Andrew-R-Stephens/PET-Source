package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.journal.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.operation.domain.journal.model.GhostType
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord

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

fun List<GhostDto>.toDomain() = map {
    it.toDomain()
}
fun GhostDto.toDomain() = GhostType(
    id = id,
    name = name
)

fun GhostDto.toLocalPopup() = GhostPopupRecord(
    id = id,
    name = name,
    info = info,
    strengthData = strengthData,
    weaknessData = weaknessData,
    huntData = huntData,
)
