package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidencepopup.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidencepopup.source.local.EvidencePopupLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository.GhostEvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.source.local.GhostEvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostpopup.repository.GhostPopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostpopup.source.local.GhostPopupLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.achievevments.CodexAchievementsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.equipment.CodexEquipmentRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.possessions.CodexPossessionsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.JournalRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalService
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.evidence.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghost.GhostType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.journal.type.ghostevidence.GhostEvidence.GhostEvidenceDto

class OperationContainer(context: Context, dataStore: DataStore<Preferences>) {

    /*
     * Investigation
     */
    val evidenceLocalDataSource: EvidenceLocalDataSource = EvidenceLocalDataSource()
    val evidenceRepository: EvidenceRepository =
        EvidenceRepository(
            context = context,
            localSource = evidenceLocalDataSource
        )

    val ghostLocalDataSource: GhostLocalDataSource = GhostLocalDataSource()
    val ghostRepository: GhostRepository =
        GhostRepository(
            context = context,
            localSource = ghostLocalDataSource
        )

    val ghostEvidenceLocalDataSource: GhostEvidenceLocalDataSource = GhostEvidenceLocalDataSource()
    val ghostEvidenceRepository: GhostEvidenceRepository =
        GhostEvidenceRepository(
            context = context,
            localSource = ghostEvidenceLocalDataSource
        )
    val journalRepository: JournalRepository = JournalRepository(
        evidenceRepository = evidenceRepository,
        ghostRepository = ghostRepository,
        ghostEvidenceRepository = ghostEvidenceRepository
    )

    val evidencePopupLocalDataSource: EvidencePopupLocalDataSource = EvidencePopupLocalDataSource()
    val evidencePopupRepository: EvidencePopupRepository = EvidencePopupRepository(
        context = context,
        localSource = evidencePopupLocalDataSource
    )

    val ghostPopupLocalDataSource: GhostPopupLocalDataSource = GhostPopupLocalDataSource()
    val ghostPopupRepository: GhostPopupRepository =
        GhostPopupRepository(
            context = context,
            localSource = ghostPopupLocalDataSource
        )

    val difficultyLocalDataSource: DifficultyLocalDataSource = DifficultyLocalDataSource()
    val difficultyRepository: DifficultyRepository =
        DifficultyRepository(
            context = context,
            localSource = difficultyLocalDataSource
        )

    val missionLocalDataSource: MissionLocalDataSource = MissionLocalDataSource()
    val missionRepository: MissionRepository =
        MissionRepository(
            context = context,
            localSource = missionLocalDataSource
        )

    /*
     * Maps
     */
    val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepository(
            context = context,
            localSource = SimpleMapLocalDataSource()
        )

    val complexMapLocalDataSource = ComplexMapLocalDataSource(
        service = ComplexMapLocalService()
    )
    val complexMapRepository: ComplexMapRepository =
        ComplexMapRepository(
            context = context,
            localSource = complexMapLocalDataSource
        )

    /*
     * Codex
     */
    val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource =
        CodexAchievementsLocalDataSource()
    val achievementsRepository: CodexAchievementsRepository =
        CodexAchievementsRepository(
            context = context,
            localSource = codexAchievementsLocalDataSource
        )
    val codexEquipmentLocalDataSource: CodexEquipmentLocalDataSource =
        CodexEquipmentLocalDataSource()
    val equipmentRepository: CodexEquipmentRepository =
        CodexEquipmentRepository(
            context = context,
            localSource = codexEquipmentLocalDataSource
        )
    val codexPossessionsLocalDataSource: CodexPossessionsLocalDataSource =
        CodexPossessionsLocalDataSource()
    val possessionsRepository: CodexPossessionsRepository =
        CodexPossessionsRepository(
            context = context,
            localSource = codexPossessionsLocalDataSource
        )
    val codexRepository: CodexRepository =
        CodexRepository(
            context = context,
            achievementsRepository = achievementsRepository,
            equipmentRepository = equipmentRepository,
            possessionsRepository = possessionsRepository
        )

}
