package com.tritiumgaming.feature.investigation.app.container

import android.content.Context
import com.tritiumgaming.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.data.codex.source.local.AchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.EquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.PossessionsLocalDataSource
import com.tritiumgaming.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.data.evidence.repository.EvidenceRepositoryImpl
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.data.ghost.repository.GhostRepositoryImpl
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.data.map.complex.repository.ComplexMapRepositoryImpl
import com.tritiumgaming.data.map.complex.source.ComplexMapDataSource
import com.tritiumgaming.data.map.complex.source.local.ComplexMapLocalDataSource
import com.tritiumgaming.data.map.complex.source.service.ComplexMapLocalService
import com.tritiumgaming.data.map.modifiers.repository.MapModifiersRepositoryImpl
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.data.map.modifiers.source.local.MapModifiersLocalDataSource
import com.tritiumgaming.data.map.simple.repository.SimpleMapRepositoryImpl
import com.tritiumgaming.data.map.simple.source.SimpleMapDataSource
import com.tritiumgaming.data.map.simple.source.local.SimpleMapLocalDataSource
import com.tritiumgaming.shared.data.codex.repository.CodexRepository
import com.tritiumgaming.shared.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.shared.data.codex.usecase.FetchPossessionTypesUseCase
import com.tritiumgaming.shared.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.data.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.GetDifficultyTypeUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.data.difficulty.usecase.SetDifficultyIndexUseCase
import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.data.evidence.usecase.GetEquipmentTypeByEvidenceTypeUseCase
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository
import com.tritiumgaming.shared.data.journal.usecase.FetchEvidenceTypesUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.data.journal.usecase.FetchGhostTypesUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetEvidenceTypeByIdUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetEvidenceUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetGhostTypeByIdUseCase
import com.tritiumgaming.shared.data.journal.usecase.GetGhostUseCase
import com.tritiumgaming.shared.data.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.shared.data.map.complex.repository.ComplexMapRepository
import com.tritiumgaming.shared.data.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.data.map.modifier.repsitory.MapModifiersRepository
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchSimpleMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementSimpleMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementSimpleMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementSimpleMapIndexUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase

class InvestigationContainer(
    applicationContext: Context,
    private val initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase
) {

    internal val preferencesUseCaseBundle = PreferencesUseCaseBundle(
        initFlowUserPreferencesUseCase = initFlowUserPreferencesUseCase
    )

    // Ghost
    private val ghostRepository: GhostRepository by lazy {
        val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource()
        GhostRepositoryImpl(
            ghostLocalDataSource = ghostLocalDataSource
        )
    }
    private val getGhostUseCase = GetGhostUseCase(
        repository = ghostRepository
    )
    private val fetchGhostTypesUseCase = FetchGhostTypesUseCase(
        repository = ghostRepository
    )
    private val getGhostTypeByIdUseCase = GetGhostTypeByIdUseCase(
        repository = ghostRepository
    )

    // Evidence
    private val evidenceRepository: EvidenceRepository by lazy {
        val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource()
        EvidenceRepositoryImpl(
            evidenceLocalDataSource = evidenceLocalDataSource
        )
    }
    private val getEvidenceUseCase = GetEvidenceUseCase(
        repository = evidenceRepository
    )
    private val fetchEvidenceTypesUseCase = FetchEvidenceTypesUseCase(
        repository = evidenceRepository
    )
    private val getEvidenceTypeByIdUseCase = GetEvidenceTypeByIdUseCase(
        repository = evidenceRepository
    )

    // Journal
    private val fetchGhostEvidencesUseCase = FetchGhostEvidencesUseCase(
        ghostRepository = ghostRepository,
        evidenceRepository = evidenceRepository
    )
    private val initRuledEvidenceUseCase = InitRuledEvidenceUseCase(
        fetchEvidencesUseCase = fetchEvidenceTypesUseCase
    )

    internal val journalUseCaseBundle: JournalUseCaseBundle = JournalUseCaseBundle(
        getEvidenceUseCase = getEvidenceUseCase,
        fetchEvidenceTypesUseCase = fetchEvidenceTypesUseCase,
        getEvidenceTypeByIdUseCase = getEvidenceTypeByIdUseCase,
        fetchGhostEvidencesUseCase = fetchGhostEvidencesUseCase,
        initRuledEvidenceUseCase = initRuledEvidenceUseCase,
        getGhostUseCase = getGhostUseCase,
        fetchGhostTypesUseCase = fetchGhostTypesUseCase,
        getGhostTypeByIdUseCase = getGhostTypeByIdUseCase
    )

    // Difficulty
    private val difficultyRepository: DifficultyRepository by lazy {
        val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource()
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )
    }
    private val fetchDifficultiesUseCase = FetchDifficultiesUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyTypeUseCase = GetDifficultyTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyNameUseCase = GetDifficultyNameUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyModifierUseCase = GetDifficultyModifierUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyTimeUseCase = GetDifficultyTimeUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyResponseTypeUseCase = GetDifficultyResponseTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    private val getDifficultyInitialSanityUseCase = GetDifficultyInitialSanityUseCase(
        difficultyRepository = difficultyRepository
    )
    private val incrementDifficultyIndexUseCase = IncrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )
    private val decrementDifficultyIndexUseCase = DecrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )
    private val setDifficultyIndexUseCase = SetDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )

    internal val difficultyUseCaseBundle = DifficultyUseCaseBundle(
        fetchDifficultiesUseCase = fetchDifficultiesUseCase,
        getDifficultyNameUseCase = getDifficultyNameUseCase,
        getDifficultyModifierUseCase = getDifficultyModifierUseCase,
        getDifficultyTimeUseCase = getDifficultyTimeUseCase,
        getDifficultyResponseTypeUseCase = getDifficultyResponseTypeUseCase,
        getDifficultyInitialSanityUseCase = getDifficultyInitialSanityUseCase,
        incrementDifficultyIndexUseCase = incrementDifficultyIndexUseCase,
        decrementDifficultyIndexUseCase = decrementDifficultyIndexUseCase,
        setDifficultyIndexUseCase = setDifficultyIndexUseCase
    )
    
    // Map Modifiers
    private val mapModifiersRepository: MapModifiersRepository by lazy {
        val mapModifiersLocalDataSource: MapModifiersDataSource = MapModifiersLocalDataSource(
            applicationContext = applicationContext
        )
        MapModifiersRepositoryImpl(
            localSource = mapModifiersLocalDataSource
        )
    }
    // Simple Map
    private val simpleMapRepository: SimpleMapRepository by lazy {
        val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
            applicationContext = applicationContext
        )
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )
    }
    
    private val fetchSimpleMapModifiersUseCase = FetchSimpleMapModifiersUseCase(
        repository = mapModifiersRepository
    )
    private val fetchSimpleMapsUseCase = FetchSimpleMapsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val fetchMapThumbnailsUseCase = FetchMapThumbnailsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val incrementSimpleMapIndexUseCase = IncrementSimpleMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val decrementSimpleMapIndexUseCase = DecrementSimpleMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val getSimpleMapIdUseCase = GetSimpleMapIdUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val getSimpleMapSetupModifierUseCase = GetSimpleMapSetupModifierUseCase(
        fetchSimpleMapModifiersUseCase = fetchSimpleMapModifiersUseCase
    )
    private val getSimpleMapNormalModifierUseCase = GetSimpleMapNormalModifierUseCase(
        fetchSimpleMapModifiersUseCase = fetchSimpleMapModifiersUseCase
    )
    private val getSimpleMapModifierUseCase = GetSimpleMapModifierUseCase(
        getSimpleMapNormalModifierUseCase = getSimpleMapNormalModifierUseCase,
        getSimpleMapSetupModifierUseCase = getSimpleMapSetupModifierUseCase
    )
    private val incrementSimpleMapFloorIndexUseCase = IncrementSimpleMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    private val decrementSimpleMapFloorIndexUseCase = DecrementSimpleMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )

    internal val simpleMapUseCaseBundle = SimpleMapUseCaseBundle(
        fetchSimpleMapsUseCase = fetchSimpleMapsUseCase,
        incrementSimpleMapIndexUseCase = incrementSimpleMapIndexUseCase,
        decrementSimpleMapIndexUseCase = decrementSimpleMapIndexUseCase,
        getSimpleMapIdUseCase = getSimpleMapIdUseCase,
        getSimpleMapNameUseCase = getSimpleMapNameUseCase,
        getSimpleMapSizeUseCase = getSimpleMapSizeUseCase,
        getSimpleMapSetupModifierUseCase = getSimpleMapSetupModifierUseCase,
        getSimpleMapNormalModifierUseCase = getSimpleMapNormalModifierUseCase,
        getSimpleMapModifierUseCase = getSimpleMapModifierUseCase,
        fetchSimpleMapModifiersUseCase = fetchSimpleMapModifiersUseCase,
        incrementSimpleMapFloorIndexUseCase = incrementSimpleMapFloorIndexUseCase,
        decrementSimpleMapFloorIndexUseCase = decrementSimpleMapFloorIndexUseCase,
        fetchMapThumbnailsUseCase = fetchMapThumbnailsUseCase
    )

    // Complex Map
    private val complexMapRepository: ComplexMapRepository by lazy {
        val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
            applicationContext = applicationContext,
            service = ComplexMapLocalService()
        )
        ComplexMapRepositoryImpl(
            localSource = complexMapLocalDataSource
        )
    }
    private val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
    )

    internal val complexMapUseCaseBundle = ComplexMapUseCaseBundle(
        fetchComplexMapsUseCase = fetchComplexMapsUseCase
    )

    // Codex
    private val codexRepository: CodexRepository by lazy {
        val achievementsLocalDataSource = AchievementsLocalDataSource()
        val equipmentLocalDataSource = EquipmentLocalDataSource()
        val possessionsLocalDataSource = PossessionsLocalDataSource()
        CodexRepositoryImpl(
            achievementsLocalDataSource = achievementsLocalDataSource,
            equipmentLocalDataSource = equipmentLocalDataSource,
            possessionsLocalDataSource = possessionsLocalDataSource
        )
    }

    private val fetchCodexAchievementsUseCase = FetchAchievementTypesUseCase(
        codexRepository = codexRepository
    )
    private val fetchCodexEquipmentUseCase = FetchEquipmentTypesUseCase(
        codexRepository = codexRepository
    )
    private val fetchCodexPossessionsUseCase = FetchPossessionTypesUseCase(
        codexRepository = codexRepository
    )

    private val getEquipmentTypeByEvidenceTypeUseCase = GetEquipmentTypeByEvidenceTypeUseCase()

    internal val codexUseCaseBundle = CodexUseCaseBundle(
        fetchCodexAchievementsUseCase = fetchCodexAchievementsUseCase,
        fetchCodexEquipmentUseCase = fetchCodexEquipmentUseCase,
        fetchCodexPossessionsUseCase = fetchCodexPossessionsUseCase,
        getEquipmentTypeByEvidenceTypeUseCase = getEquipmentTypeByEvidenceTypeUseCase
    )
}

internal data class JournalUseCaseBundle(
    val getGhostUseCase: GetGhostUseCase,
    val fetchGhostTypesUseCase: FetchGhostTypesUseCase,
    val getGhostTypeByIdUseCase: GetGhostTypeByIdUseCase,
    val getEvidenceUseCase: GetEvidenceUseCase,
    val fetchEvidenceTypesUseCase: FetchEvidenceTypesUseCase,
    val getEvidenceTypeByIdUseCase: GetEvidenceTypeByIdUseCase,
    val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase,
    val initRuledEvidenceUseCase: InitRuledEvidenceUseCase,
)

internal data class DifficultyUseCaseBundle(
    val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
    val getDifficultyNameUseCase: GetDifficultyNameUseCase,
    val getDifficultyModifierUseCase: GetDifficultyModifierUseCase,
    val getDifficultyTimeUseCase: GetDifficultyTimeUseCase,
    val getDifficultyResponseTypeUseCase: GetDifficultyResponseTypeUseCase,
    val getDifficultyInitialSanityUseCase: GetDifficultyInitialSanityUseCase,
    val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase,
    val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase,
    val setDifficultyIndexUseCase: SetDifficultyIndexUseCase,
)

internal data class SimpleMapUseCaseBundle(
    val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    val incrementSimpleMapIndexUseCase: IncrementSimpleMapIndexUseCase,
    val decrementSimpleMapIndexUseCase: DecrementSimpleMapIndexUseCase,
    val getSimpleMapIdUseCase: GetSimpleMapIdUseCase,
    val getSimpleMapNameUseCase: GetSimpleMapNameUseCase,
    val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase,
    val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase,
    val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    val getSimpleMapModifierUseCase: GetSimpleMapModifierUseCase,
    val fetchSimpleMapModifiersUseCase: FetchSimpleMapModifiersUseCase,
    val incrementSimpleMapFloorIndexUseCase: IncrementSimpleMapFloorIndexUseCase,
    val decrementSimpleMapFloorIndexUseCase: DecrementSimpleMapFloorIndexUseCase,
    val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
)

internal data class ComplexMapUseCaseBundle(
    val fetchComplexMapsUseCase: FetchComplexMapsUseCase,
)

internal data class CodexUseCaseBundle(
    val fetchCodexAchievementsUseCase: FetchAchievementTypesUseCase,
    val fetchCodexEquipmentUseCase: FetchEquipmentTypesUseCase,
    val fetchCodexPossessionsUseCase: FetchPossessionTypesUseCase,
    val getEquipmentTypeByEvidenceTypeUseCase: GetEquipmentTypeByEvidenceTypeUseCase,
)

internal data class PreferencesUseCaseBundle(
    val initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase
)
