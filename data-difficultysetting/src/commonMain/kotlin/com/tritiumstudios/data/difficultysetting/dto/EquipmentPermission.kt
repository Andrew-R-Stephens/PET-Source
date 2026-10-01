package com.tritiumstudios.data.difficultysetting.dto

import com.tritiumgaming.data.codex.mappers.EquipmentResources.EquipmentIdentifier

data class EquipmentPermission(
    val identifier: EquipmentIdentifier,
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