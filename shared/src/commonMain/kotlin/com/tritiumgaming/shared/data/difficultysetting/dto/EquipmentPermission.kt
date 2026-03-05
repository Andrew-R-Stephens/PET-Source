package com.tritiumgaming.shared.data.difficultysetting.dto

import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources

data class EquipmentPermission(
    val identifier: EquipmentResources.EquipmentIdentifier,
    val quantity: Int = ALL,
    val permission: Permission = Permission.REVOKED
) {
    enum class Permission {
        PERMITTED,
        REVOKED
    }

    companion object {
        const val ALL = Int.MAX_VALUE
    }
}