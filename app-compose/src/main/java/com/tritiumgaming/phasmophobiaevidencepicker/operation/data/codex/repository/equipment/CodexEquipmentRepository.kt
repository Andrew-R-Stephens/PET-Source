package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.equipment

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.codex.CodexGroups

class CodexEquipmentRepository(
    context: Context,
    val localSource: CodexEquipmentLocalDataSource
) {

    val equipment = fetchEquipment(context)

    fun fetchEquipment(context: Context): CodexGroups {
        return localSource.fetchEquipment(context = context)
    }

}
