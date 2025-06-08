package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexRepository {

    val achievementsRepository: CodexTypeRepository
    val equipmentRepository: CodexTypeRepository
    val possessionsRepository: CodexTypeRepository

    fun fetchAchievements(): Result<CodexGroups>
    fun fetchEquipment(): Result<CodexGroups>
    fun fetchPossessions(): Result<CodexGroups>

}