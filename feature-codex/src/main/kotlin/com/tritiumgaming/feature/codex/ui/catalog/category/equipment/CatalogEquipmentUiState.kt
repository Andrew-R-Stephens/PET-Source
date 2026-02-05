package com.tritiumgaming.feature.codex.ui.catalog.category.equipment

import androidx.annotation.IntegerRes
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier

data class CatalogEquipmentUiState(
    val list: List<EquipmentType> = emptyList(),
    val selectedGroup: EquipmentType? = null,
    val selectedItem: EquipmentTypeTier? = null,
    @field:IntegerRes val icons: List<Int> = emptyList()
)
