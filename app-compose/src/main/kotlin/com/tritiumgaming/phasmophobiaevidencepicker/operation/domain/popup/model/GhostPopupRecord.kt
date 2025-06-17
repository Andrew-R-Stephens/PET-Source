package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostDescription
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostStrength
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.mapper.GhostResources.GhostWeakness

data class GhostPopupRecord(
    val id: String = "0",
    val name: GhostTitle,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
): PopupRecord()
