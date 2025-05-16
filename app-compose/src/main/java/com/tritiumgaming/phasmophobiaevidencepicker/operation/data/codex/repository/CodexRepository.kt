package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.possessions.CodexPossessionsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.codex.CodexGroups

class CodexRepository(
    context: Context,
    val achievementsRepository: CodexAchievementsRepository,
    val equipmentRepository: CodexEquipmentRepository,
    val possessionsRepository: CodexPossessionsRepository
) {

    val achievements = fetchAchievements(context)
    val equipment = fetchEquipment(context)
    val possessions = fetchPossessions(context)

    fun fetchAchievements(context: Context): CodexGroups {
        return achievementsRepository.fetchAchievements(context = context)
    }

    fun fetchEquipment(context: Context): CodexGroups {
        return equipmentRepository.fetchEquipment(context = context)
    }

    fun fetchPossessions(context: Context): CodexGroups {
        return possessionsRepository.fetchPossessions(context = context)
    }

}