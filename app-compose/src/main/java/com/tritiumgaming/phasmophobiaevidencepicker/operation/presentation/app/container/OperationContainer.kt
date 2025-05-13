package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.GhostEvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.codex.possessions.CodexPossessionsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.EvidencePopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType

class OperationContainer(context: Context, dataStore: DataStore<Preferences>) {

    /*
     * Investigation
     */
    val evidenceLocalDataSource: EvidenceLocalDataSource = EvidenceLocalDataSource(
        context = context
    )
    val ghostEvidenceLocalDataSource: GhostEvidenceLocalDataSource = GhostEvidenceLocalDataSource(
        context = context,
        evidenceLocalSource = evidenceLocalDataSource
    )
    val evidenceRepository: EvidenceRepository =
        EvidenceRepository(
            localSource = evidenceLocalDataSource
        )

    val ghostLocalDataSource: GhostLocalDataSource = GhostLocalDataSource(
        context = context
    )
    val ghostRepository: GhostRepository =
        GhostRepository(
            ghostLocalSource = ghostLocalDataSource,
            ghostEvidenceLocalSource = ghostEvidenceLocalDataSource
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
