package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository


import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.possessions.CodexPossessionsRepository

class CodexRepository(
    context: Context
) {

    val achievements = CodexAchievementsRepository(context)
    val equipment = CodexEquipmentRepository(context)
    val possessions = CodexPossessionsRepository(context)

}