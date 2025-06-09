package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexAchievementsRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexEquipmentRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexPossessionsRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository.EvidenceRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository.GhostRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository.GhostEvidenceRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.source.local.GhostEvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.JournalRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalService
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.repository.EvidencePopupRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.source.local.EvidencePopupLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.repository.GhostPopupRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.ghost.source.local.GhostPopupLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexTypeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.source.CodexDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository.GhostEvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source.GhostEvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class OperationContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    /*
     * Investigation Journal
     */

    // Evidence
    val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource(applicationContext)
    val evidenceRepository: EvidenceRepository =
        EvidenceRepositoryImpl(
            localSource = evidenceLocalDataSource
        )

    // Ghost
    val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource(applicationContext)
    val ghostRepository: GhostRepository =
        GhostRepositoryImpl(
            localSource = ghostLocalDataSource
        )

    // Ghost Evidence
    val ghostEvidenceLocalDataSource: GhostEvidenceDataSource = GhostEvidenceLocalDataSource(applicationContext)
    val ghostEvidenceRepository: GhostEvidenceRepository =
        GhostEvidenceRepositoryImpl(
            localSource = ghostEvidenceLocalDataSource
        )

    // Journal
    val journalRepository: JournalRepository = JournalRepositoryImpl(
        evidenceRepository = evidenceRepository,
        ghostRepository = ghostRepository,
        ghostEvidenceRepository = ghostEvidenceRepository
    )

    // Evidence Popup
    val evidencePopupLocalDataSource: EvidencePopupDataSource = EvidencePopupLocalDataSource(applicationContext)
    val evidencePopupRepository: EvidencePopupRepositoryImpl =
        EvidencePopupRepositoryImpl(
            localSource = evidencePopupLocalDataSource
    )

    // Ghost Popup
    val ghostPopupLocalDataSource: GhostPopupDataSource = GhostPopupLocalDataSource(applicationContext)
    val ghostPopupRepository: GhostPopupRepositoryImpl =
        GhostPopupRepositoryImpl(
            localSource = ghostPopupLocalDataSource
        )

    // Difficulty
    val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(applicationContext)
    val difficultyRepository: DifficultyRepository =
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )

    // Mission
    val missionLocalDataSource: MissionDataSource = MissionLocalDataSource()
    val missionRepository: MissionRepository =
        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )

    /*
     * Interactive Map
     */
    // Simple Map
    val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(applicationContext)
    val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )

    // Complex Map
    val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
        applicationContext = applicationContext,
        service = ComplexMapLocalService()
    )
    val complexMapRepository: ComplexMapRepository =
        ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )

    /*
     * Codex
     */

    // Achievements
    val codexAchievementsLocalDataSource: CodexDataSource =
        CodexAchievementsLocalDataSource(applicationContext)
    val achievementsRepository: CodexTypeRepository =
        CodexAchievementsRepositoryImpl(
            localSource = codexAchievementsLocalDataSource
        )

    // Equipment
    val codexEquipmentLocalDataSource: CodexDataSource =
        CodexEquipmentLocalDataSource(applicationContext)
    val equipmentRepository: CodexTypeRepository =
        CodexEquipmentRepositoryImpl(
            localSource = codexEquipmentLocalDataSource
        )

    //Possessions
    val codexPossessionsLocalDataSource: CodexDataSource =
        CodexPossessionsLocalDataSource(applicationContext)
    val possessionsRepository: CodexTypeRepository =
        CodexPossessionsRepositoryImpl(
            localSource = codexPossessionsLocalDataSource
        )

    // Codex
    val codexRepository: CodexRepository =
        CodexRepositoryImpl(
            achievementsRepository = achievementsRepository,
            equipmentRepository = equipmentRepository,
            possessionsRepository = possessionsRepository
        )

}
