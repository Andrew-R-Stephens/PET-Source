package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexTypeRepository

class CodexRepositoryImpl(
    override val achievementsRepository: CodexTypeRepository,
    override val equipmentRepository: CodexTypeRepository,
    override val possessionsRepository: CodexTypeRepository
): CodexRepository {

    override fun fetchAchievements(): CodexGroups {
        return achievementsRepository.fetchItems()
    }

    override fun fetchEquipment(): CodexGroups {
        return equipmentRepository.fetchItems()
    }

    override fun fetchPossessions(): CodexGroups {
        return possessionsRepository.fetchItems()
    }

}