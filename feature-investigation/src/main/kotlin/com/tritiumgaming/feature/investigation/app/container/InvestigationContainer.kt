package com.tritiumgaming.feature.investigation.app.container

import android.content.Context
import com.tritiumgaming.data.challenge.usecase.GetCurrentChallengeUseCase
import com.tritiumgaming.data.codex.repository.CodexRepository
import com.tritiumgaming.data.codex.repository.CodexRepositoryImpl
import com.tritiumgaming.data.codex.source.local.AchievementsLocalDataSource
import com.tritiumgaming.data.codex.source.local.EquipmentLocalDataSource
import com.tritiumgaming.data.codex.source.local.PossessionsLocalDataSource
import com.tritiumgaming.data.codex.usecase.FetchAchievementTypesUseCase
import com.tritiumgaming.data.codex.usecase.FetchEquipmentTypesUseCase
import com.tritiumgaming.data.codex.usecase.FetchPossessionTypesUseCase
import com.tritiumgaming.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.data.difficulty.repository.DifficultyRepository
import com.tritiumgaming.data.difficulty.repository.DifficultyRepositoryImpl
import com.tritiumgaming.data.difficulty.source.DifficultyDataSource
import com.tritiumgaming.data.difficulty.source.local.DifficultyLocalDataSource
import com.tritiumgaming.data.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.data.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.data.difficulty.usecase.GetDifficultyTypeUseCase
import com.tritiumgaming.data.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.data.difficulty.usecase.SetDifficultyIndexUseCase
import com.tritiumgaming.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.data.evidence.repository.EvidenceRepositoryImpl
import com.tritiumgaming.data.evidence.source.EvidenceDataSource
import com.tritiumgaming.data.evidence.source.local.EvidenceLocalDataSource
import com.tritiumgaming.data.evidence.usecase.GetEquipmentTypeByEvidenceTypeUseCase
import com.tritiumgaming.data.ghost.repository.GhostRepository
import com.tritiumgaming.data.ghost.repository.GhostRepositoryImpl
import com.tritiumgaming.data.ghost.source.GhostDataSource
import com.tritiumgaming.data.ghost.source.local.GhostLocalDataSource
import com.tritiumgaming.data.journal.usecase.FetchEvidenceTypesUseCase
import com.tritiumgaming.data.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.data.journal.usecase.FetchGhostTypesUseCase
import com.tritiumgaming.data.journal.usecase.GetEvidenceTypeByIdUseCase
import com.tritiumgaming.data.journal.usecase.GetEvidenceUseCase
import com.tritiumgaming.data.journal.usecase.GetGhostTypeByIdUseCase
import com.tritiumgaming.data.journal.usecase.GetGhostUseCase
import com.tritiumgaming.data.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.data.map.modifiers.repository.MapModifiersRepositoryImpl
import com.tritiumgaming.data.map.modifiers.source.MapModifiersDataSource
import com.tritiumgaming.data.map.modifiers.source.local.MapModifiersLocalDataSource
import com.tritiumgaming.data.map.repository.modifier.MapModifiersRepository
import com.tritiumgaming.data.map.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.data.map.usecase.GetMapModifierUseCase
import com.tritiumgaming.data.map.usecase.GetMapNormalModifierUseCase
import com.tritiumgaming.data.map.usecase.GetMapSetupModifierUseCase
import com.tritiumgaming.data.map.usecase.GetMapsFlowUseCase
import com.tritiumgaming.data.trait.repository.GhostTraitRepository
import com.tritiumgaming.data.trait.repository.GhostTraitRepositoryImpl
import com.tritiumgaming.data.trait.source.local.GhostTraitLocalDataSource
import com.tritiumgaming.data.trait.usecase.GetAllGhostTraitsUseCase
import com.tritiumgaming.data.trait.usecase.GetGhostTraitDescriptionUseCase
import com.tritiumgaming.data.trait.usecase.GetGhostTraitsByCategoryUseCase
import com.tritiumgaming.data.trait.usecase.GetGhostTraitsByGhostUseCase
import com.tritiumgaming.data.trait.usecase.GetGhostTraitsByTagUseCase
import com.tritiumgaming.data.trait.usecase.GetUniqueGhostTraitsUseCase
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumstudios.data.operation.usecase.bundle.InvestigationUseCaseBundle

class InvestigationContainer(
    applicationContext: Context,
    val investigationUseCaseBundle: InvestigationUseCaseBundle,
    private val initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val getCurrentChallengeUseCase: GetCurrentChallengeUseCase,
    internal val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase,
    internal val getMapsFlowUseCase: GetMapsFlowUseCase,
) {

    internal val challengesUseCaseBundle = ChallengesUseCaseBundle(
        getCurrentChallengeUseCase = getCurrentChallengeUseCase
    )

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

    private val ghostTraitsRepository: GhostTraitRepository by lazy {
        val dataSource = GhostTraitLocalDataSource()
        GhostTraitRepositoryImpl(
            applicationContext = applicationContext,
            localSource = dataSource
        )
    }
    private val getAllGhostTraitsUseCase = GetAllGhostTraitsUseCase(
        repository = ghostTraitsRepository
    )
    private val getGhostTraitDescriptionUseCase = GetGhostTraitDescriptionUseCase(
        repository = ghostTraitsRepository
    )
    private val getGhostTraitsByCategoryUseCase = GetGhostTraitsByCategoryUseCase(
        repository = ghostTraitsRepository
    )
    private val getGhostTraitsByGhostUseCase = GetGhostTraitsByGhostUseCase(
        repository = ghostTraitsRepository
    )
    private val getGhostTraitsByTagUseCase = GetGhostTraitsByTagUseCase(
        repository = ghostTraitsRepository
    )
    private val getUniqueGhostTraitsUseCase = GetUniqueGhostTraitsUseCase(
        repository = ghostTraitsRepository
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
        getGhostTypeByIdUseCase = getGhostTypeByIdUseCase,
        getAllGhostTraitsUseCase = getAllGhostTraitsUseCase,
        getGhostTraitDescriptionUseCase = getGhostTraitDescriptionUseCase,
        getGhostTraitsByCategoryUseCase = getGhostTraitsByCategoryUseCase,
        getGhostTraitsByGhostUseCase = getGhostTraitsByGhostUseCase,
        getGhostTraitsByTagUseCase = getGhostTraitsByTagUseCase,
        getUniqueGhostTraitsUseCase = getUniqueGhostTraitsUseCase
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
        val mapModifiersLocalDataSource: MapModifiersDataSource = MapModifiersLocalDataSource()
        MapModifiersRepositoryImpl(
            localSource = mapModifiersLocalDataSource
        )
    }

    private val fetchMapModifiersUseCase = FetchMapModifiersUseCase(
        repository = mapModifiersRepository
    )
    private val getMapSetupModifierUseCase = GetMapSetupModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    private val getMapNormalModifierUseCase = GetMapNormalModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    private val getMapModifierUseCase = GetMapModifierUseCase(
        getMapNormalModifierUseCase = getMapNormalModifierUseCase,
        getMapSetupModifierUseCase = getMapSetupModifierUseCase
    )

    internal val mapUseCaseBundle = MapUseCaseBundle(
        getMapsFlowUseCase = getMapsFlowUseCase,
        getMapSetupModifierUseCase = getMapSetupModifierUseCase,
        getMapNormalModifierUseCase = getMapNormalModifierUseCase,
        getMapModifierUseCase = getMapModifierUseCase,
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
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
    val getAllGhostTraitsUseCase: GetAllGhostTraitsUseCase,
    val getGhostTraitDescriptionUseCase: GetGhostTraitDescriptionUseCase,
    val getGhostTraitsByCategoryUseCase: GetGhostTraitsByCategoryUseCase,
    val getGhostTraitsByGhostUseCase: GetGhostTraitsByGhostUseCase,
    val getGhostTraitsByTagUseCase: GetGhostTraitsByTagUseCase,
    val getUniqueGhostTraitsUseCase: GetUniqueGhostTraitsUseCase
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

internal data class MapUseCaseBundle(
    val getMapsFlowUseCase: GetMapsFlowUseCase,
    val getMapSetupModifierUseCase: GetMapSetupModifierUseCase,
    val getMapNormalModifierUseCase: GetMapNormalModifierUseCase,
    val getMapModifierUseCase: GetMapModifierUseCase,
    val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
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

internal data class ChallengesUseCaseBundle(
    val getCurrentChallengeUseCase: GetCurrentChallengeUseCase
)