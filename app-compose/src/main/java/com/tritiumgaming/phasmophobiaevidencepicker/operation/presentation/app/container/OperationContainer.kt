package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.possessions.CodexPossessionsRepository

class OperationContainer(context: Context, dataStore: DataStore<Preferences>) {

    /*
     * Investigation
     */
    val evidenceRepository: EvidenceRepository =
        EvidenceRepository(
            context = context
        )

    val ghostRepository: GhostRepository =
        GhostRepository(
            evidenceRepository = evidenceRepository,
            context = context
        )

    val difficultyRepository: DifficultyRepository =
        DifficultyRepository(
            context = context
        )

    val missionRepository: MissionRepository =
        MissionRepository(
            context = context
        )

    /*
     * Maps
     */
    val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepository(
            context = context
        )

    val complexMapRepository: ComplexMapRepository =
        ComplexMapRepository(
            context = context
        )

    /*
     * Codex
     */
    val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource =
        CodexAchievementsLocalDataSource(
            context = context
        )
    val codexAchievementsRepository: CodexAchievementsRepository = CodexAchievementsRepository(
        codexAchievementsLocalDataSource = codexAchievementsLocalDataSource
    )
    val codexEquipment: CodexEquipmentRepository = CodexEquipmentRepository(
        context
    )
    val codexPossessions: CodexPossessionsRepository = CodexPossessionsRepository(
        context
    )
    val codexRepository: CodexRepository =
        CodexRepository(
            achievements = codexAchievementsRepository,
            equipment = codexEquipment,
            possessions = codexPossessions
        )

}