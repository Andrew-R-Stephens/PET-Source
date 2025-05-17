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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.local.DifficultyDataSource
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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.repository.GhostPopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.GhostPopupDataSource

class OperationContainer(context: Context, dataStore: DataStore<Preferences>) {

    /*
     * Investigation Journal
     */
    val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource()
    val evidenceRepository: EvidenceRepository =
        EvidenceRepositoryImpl(
            context = context,
            localSource = evidenceLocalDataSource
        )

    val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource()
    val ghostRepository: GhostRepository =
        GhostRepositoryImpl(
            context = context,
            localSource = ghostLocalDataSource
        )

    val ghostEvidenceLocalDataSource: GhostEvidenceDataSource = GhostEvidenceLocalDataSource()
    val ghostEvidenceRepository: GhostEvidenceRepository =
        GhostEvidenceRepositoryImpl(
            context = context,
            localSource = ghostEvidenceLocalDataSource
        )
    val journalRepository: JournalRepository = JournalRepositoryImpl(
        evidenceRepository = evidenceRepository,
        ghostRepository = ghostRepository,
        ghostEvidenceRepository = ghostEvidenceRepository
    )

    val evidencePopupLocalDataSource: EvidencePopupDataSource = EvidencePopupLocalDataSource()
    val evidencePopupRepository: EvidencePopupRepository =
        EvidencePopupRepositoryImpl(
            context = context,
            localSource = evidencePopupLocalDataSource
    )

    val ghostPopupLocalDataSource: GhostPopupDataSource = GhostPopupLocalDataSource()
    val ghostPopupRepository: GhostPopupRepository =
        GhostPopupRepositoryImpl(
            context = context,
            localSource = ghostPopupLocalDataSource
        )

    /*
     * Investigation Difficulties
     */
    val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource()
    val difficultyRepository: DifficultyRepository =
        DifficultyRepositoryImpl(
            context = context,
            localSource = difficultyLocalDataSource
        )

    /*
     * Investigation Missions
     */
    val missionLocalDataSource: MissionDataSource = MissionLocalDataSource()
    val missionRepository: MissionRepository =
        MissionRepositoryImpl(
            context = context,
            localSource = missionLocalDataSource
        )

    /*
     * Maps
     */
    val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource()
    val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepositoryImpl(
            context = context,
            localSource = simpleMapLocalDataSource
        )

    val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
        service = ComplexMapLocalService()
    )
    val complexMapRepository: ComplexMapRepository =
        ComplexMapRepositoryImpl(
            context = context,
            localSource = complexMapLocalDataSource
        )

    /*
     * Codex
     */
    val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource =
        CodexAchievementsLocalDataSource()
    val achievementsRepository: CodexTypeRepository =
        CodexAchievementsRepositoryImpl(
            context = context,
            localSource = codexAchievementsLocalDataSource
        )

    val codexEquipmentLocalDataSource: CodexEquipmentLocalDataSource =
        CodexEquipmentLocalDataSource()
    val equipmentRepository: CodexTypeRepository =
        CodexEquipmentRepositoryImpl(
            context = context,
            localSource = codexEquipmentLocalDataSource
        )

    val codexPossessionsLocalDataSource: CodexPossessionsLocalDataSource =
        CodexPossessionsLocalDataSource()
    val possessionsRepository: CodexTypeRepository =
        CodexPossessionsRepositoryImpl(
            context = context,
            localSource = codexPossessionsLocalDataSource
        )

    val codexRepository: CodexRepository =
        CodexRepositoryImpl(
            context = context,
            achievementsRepository = achievementsRepository,
            equipmentRepository = equipmentRepository,
            possessionsRepository = possessionsRepository
        )

}
