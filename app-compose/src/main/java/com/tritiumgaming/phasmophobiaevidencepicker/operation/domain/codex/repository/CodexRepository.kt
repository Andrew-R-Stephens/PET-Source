package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.CodexGroups

interface CodexRepository {
    val achievementsRepository: CodexTypeRepository
    val equipmentRepository: CodexTypeRepository
    val possessionsRepository: CodexTypeRepository

    val achievements : CodexGroups
    val equipment : CodexGroups
    val possessions : CodexGroups

    fun fetchAchievements(context: Context): CodexGroups
    fun fetchEquipment(context: Context): CodexGroups
    fun fetchPossessions(context: Context): CodexGroups

}