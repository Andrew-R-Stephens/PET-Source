package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexTypeRepository

class CodexRepositoryImpl(
    context: Context,
    override val achievementsRepository: CodexTypeRepository,
    override val equipmentRepository: CodexTypeRepository,
    override val possessionsRepository: CodexTypeRepository
): CodexRepository {

    override val achievements = fetchAchievements(context)
    override val equipment = fetchEquipment(context)
    override val possessions = fetchPossessions(context)

    override fun fetchAchievements(context: Context): CodexGroups {
        return achievementsRepository.fetchItems(context = context)
    }

    override fun fetchEquipment(context: Context): CodexGroups {
        return equipmentRepository.fetchItems(context = context)
    }

    override fun fetchPossessions(context: Context): CodexGroups {
        return possessionsRepository.fetchItems(context = context)
    }

}