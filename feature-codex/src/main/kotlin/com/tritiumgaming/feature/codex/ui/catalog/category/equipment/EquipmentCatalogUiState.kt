package com.tritiumgaming.feature.codex.ui.catalog.category.equipment

import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentType
import com.tritiumgaming.shared.data.codex.model.equipment.EquipmentTypeTier

data class EquipmentCatalogUiState(
    val list: List<EquipmentType> = emptyList(),
    val selectedGroup: EquipmentType? = null,
    val selectedItem: EquipmentTypeTier? = null,
    )