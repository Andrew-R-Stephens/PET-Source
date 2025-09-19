package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.container

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexAchievementsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexEquipmentLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.codex.source.local.CodexPossessionsLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.repository.GhostNameRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.source.GhostNameDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostname.source.local.GhostNameLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository.new.JournalRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.GhostDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local.EvidenceLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.source.local.GhostLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.repository.MapModifiersRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.modifiers.source.local.MapModifiersLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.repository.MissionRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.MissionDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.mission.source.local.MissionLocalDataSource
import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexAchievementsUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexEquipmentUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexPossessionsUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.repository.GhostNameRepository
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetEvidenceByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetGhostByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.shared.operation.domain.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.shared.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.repsitory.MapModifiersRepository
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.mission.repository.MissionRepository
import com.tritiumgaming.shared.operation.domain.mission.usecase.FetchAllMissionsUseCase

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
    internal val initRuledEvidenceUseCase = InitRuledEvidenceUseCase(fetchEvidencesUseCase)

    // Difficulty
    private val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(applicationContext)
    private val difficultyRepository: DifficultyRepository =
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )
    internal val fetchDifficultiesUseCase = FetchDifficultiesUseCase(difficultyRepository)
    internal val getDifficultyTypeUseCase = GetDifficultyTypeUseCase(difficultyRepository)
    internal val getDifficultyNameUseCase = GetDifficultyNameUseCase(difficultyRepository)
    internal val getDifficultyModifierUseCase = GetDifficultyModifierUseCase(difficultyRepository)
    internal val getDifficultyTimeUseCase = GetDifficultyTimeUseCase(difficultyRepository)
    internal val getDifficultyResponseTypeUseCase = GetDifficultyResponseTypeUseCase(difficultyRepository)
    internal val getDifficultyInitialSanityUseCase = GetDifficultyInitialSanityUseCase(difficultyRepository)
    internal val incrementDifficultyIndexUseCase = IncrementDifficultyIndexUseCase(difficultyRepository)
    internal val decrementDifficultyIndexUseCase = DecrementDifficultyIndexUseCase(difficultyRepository)

    // Mission
    private val missionLocalDataSource: MissionDataSource = MissionLocalDataSource(
        applicationContext = applicationContext,
    )
    internal val missionRepository: MissionRepository =
        MissionRepositoryImpl(
            localSource = missionLocalDataSource
        )
    internal val fetchAllMissionsUseCase = FetchAllMissionsUseCase(missionRepository)

    // Ghost Name
    private val ghostNameLocalDataSource: GhostNameDataSource = GhostNameLocalDataSource(
        applicationContext = applicationContext,
    )
    internal val ghostNameRepository: GhostNameRepository =
        GhostNameRepositoryImpl(
            localSource = ghostNameLocalDataSource
        )
    internal val fetchAllFirstNamesUseCase = FetchAllFirstNamesUseCase(ghostNameRepository)
    internal val fetchAllMaleNamesUseCase = FetchAllMaleNamesUseCase(ghostNameRepository)
    internal val fetchAllFemaleNamesUseCase = FetchAllFemaleNamesUseCase(ghostNameRepository)
    internal val fetchAllSurnamesUseCase = FetchAllSurnamesUseCase(ghostNameRepository)

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
    internal val getSimpleMapIdUseCase = GetSimpleMapIdUseCase(simpleMapRepository)
    internal val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(simpleMapRepository)
    internal val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(simpleMapRepository)
    internal val getSimpleMapSetupModifierUseCase = GetSimpleMapSetupModifierUseCase(fetchMapModifiersUseCase)
    internal val getSimpleMapNormalModifierUseCase = GetSimpleMapNormalModifierUseCase(fetchMapModifiersUseCase)
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
        CodexEquipmentLocalDataSource()

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
