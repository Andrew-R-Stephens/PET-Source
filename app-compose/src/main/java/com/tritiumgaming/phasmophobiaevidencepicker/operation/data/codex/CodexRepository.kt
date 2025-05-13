package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.possessions.CodexPossessionsRepository

class CodexRepository(
    val achievements: CodexAchievementsRepository,
    val equipment: CodexEquipmentRepository,
    val possessions: CodexPossessionsRepository
) {

}