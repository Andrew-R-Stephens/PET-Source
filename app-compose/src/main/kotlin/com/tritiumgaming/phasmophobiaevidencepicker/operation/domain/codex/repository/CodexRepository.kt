package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexRepository {

    val achievementsRepository: CodexTypeRepository
    val equipmentRepository: CodexTypeRepository
    val possessionsRepository: CodexTypeRepository

    fun fetchAchievements(): CodexGroups
    fun fetchEquipment(): CodexGroups
    fun fetchPossessions(): CodexGroups

}