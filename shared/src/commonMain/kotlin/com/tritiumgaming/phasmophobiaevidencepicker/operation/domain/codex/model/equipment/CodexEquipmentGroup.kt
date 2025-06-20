package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentBuyCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles

data class CodexEquipmentGroup(
    val name: EquipmentTitles,
    val icon: EquipmentIcon,
    val buyCostData: EquipmentBuyCost,
    val items: List<CodexEquipmentGroupItem>
) {
    val size: Int = items.size
}
