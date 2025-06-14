package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.new.JournalRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.service.ComplexMapLocalService
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.repository.MapModifiersRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.local.MapModifiersLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.source.DifficultyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexAchievementsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexEquipmentUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase.FetchCodexPossessionsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.GetEvidenceByIdUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase.GetGhostByIdUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.repsitory.MapModifiersRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifiers.source.local.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.GetMapModifierUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.source.MissionDataSource

class OperationContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    /*
     * Investigation Journal
     */

    // Journal
    private val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource(applicationContext)
    private val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource(applicationContext)
    private val journalRepository: JournalRepository = JournalRepositoryImpl(
        ghostLocalDataSource = ghostLocalDataSource,
        evidenceLocalDataSource = evidenceLocalDataSource
    )
    internal val fetchGhostsUseCase = FetchGhostsUseCase(journalRepository)
    internal val fetchEvidencesUseCase = FetchEvidencesUseCase(journalRepository)
    internal val fetchGhostEvidencesUseCase = FetchGhostEvidencesUseCase(journalRepository)
    internal val getGhostByIdUseCase = GetGhostByIdUseCase(journalRepository)
    internal val getEvidenceByIdUseCase = GetEvidenceByIdUseCase(journalRepository)

    // Difficulty
    private val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(applicationContext)
    private val difficultyRepository: DifficultyRepository =
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )
    internal val fetchDifficultiesUseCase = FetchDifficultiesUseCase(difficultyRepository)

    // Mission
    private val missionLocalDataSource: MissionDataSource = MissionLocalDataSource()
    internal val missionRepository: MissionRepository =
        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )

    // Map Modifiers
    private val mapModifiersLocalDataSource: MapModifiersDataSource =
        MapModifiersLocalDataSource(applicationContext)
    private val mapModifiersRepository: MapModifiersRepository = MapModifiersRepositoryImpl(
        localSource = mapModifiersLocalDataSource
    )
    internal val fetchMapModifiersUseCase = FetchMapModifiersUseCase(mapModifiersRepository)

    // Simple Map
    private val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(applicationContext)
    private val simpleMapRepository: SimpleMapRepository =
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )
    internal val fetchSimpleMapsUseCase = FetchSimpleMapsUseCase(simpleMapRepository)
    internal val fetchMapThumbnailsUseCase = FetchMapThumbnailsUseCase(simpleMapRepository)
    internal val incrementMapIndexUseCase = IncrementMapIndexUseCase(simpleMapRepository)
    internal val decrementMapIndexUseCase = DecrementMapIndexUseCase(simpleMapRepository)
    internal val incrementMapFloorIndexUseCase = IncrementMapFloorIndexUseCase(simpleMapRepository)
    internal val decrementMapFloorIndexUseCase = DecrementMapFloorIndexUseCase(simpleMapRepository)
    internal val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(simpleMapRepository)
    internal val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(simpleMapRepository)
    internal val getSimpleMapSetupModifierUseCase = GetSimpleMapSetupModifierUseCase(simpleMapRepository)
    internal val getSimpleMapNormalModifierUseCase = GetSimpleMapNormalModifierUseCase(simpleMapRepository)
    internal val getMapModifierUseCase = GetMapModifierUseCase(
        getSimpleMapNormalModifierUseCase, getSimpleMapSetupModifierUseCase)

    // Complex Map
    private val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
        applicationContext = applicationContext,
        service = ComplexMapLocalService()
    )
    private val complexMapRepository: ComplexMapRepository =
        ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )
    internal val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
    )

    /*
     * Codex
     */

    // Achievements
    private val codexAchievementsLocalDataSource: CodexAchievementsLocalDataSource =
        CodexAchievementsLocalDataSource()

    // Equipment
    private val codexEquipmentLocalDataSource: CodexEquipmentLocalDataSource =
        CodexEquipmentLocalDataSource(applicationContext)

    //Possessions
    private val codexPossessionsLocalDataSource: CodexPossessionsLocalDataSource =
        CodexPossessionsLocalDataSource()

    // Codex
    private val codexRepository: CodexRepository =
        CodexRepositoryImpl(
            achievementsLocalDataSource = codexAchievementsLocalDataSource,
            equipmentLocalDataSource = codexEquipmentLocalDataSource,
            possessionsLocalDataSource = codexPossessionsLocalDataSource
        )
    internal val fetchCodexAchievementsUseCase = FetchCodexAchievementsUseCase(codexRepository)
    internal val fetchCodexEquipmentUseCase = FetchCodexEquipmentUseCase(codexRepository)
    internal val fetchCodexPossessionsUseCase = FetchCodexPossessionsUseCase(codexRepository)

}
