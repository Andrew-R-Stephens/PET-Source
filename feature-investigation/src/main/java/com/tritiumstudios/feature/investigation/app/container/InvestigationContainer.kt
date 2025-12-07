package com.tritiumstudios.feature.investigation.app.container

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
import com.tritiumgaming.shared.data.preferences.usecase.GetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.GetMaxHuntWarnFlashTimeUseCase
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
import com.tritiumgaming.shared.data.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.data.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.data.map.simple.repository.SimpleMapRepository
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.data.map.simple.usecase.IncrementMapIndexUseCase

class InvestigationContainer(
    applicationContext: Context,
    val getAllowHuntWarnAudioUseCase: GetAllowHuntWarnAudioUseCase,
    val getEnableGhostReorderUseCase: GetEnableGhostReorderUseCase,
    val getEnableRTLUseCase: GetEnableRTLUseCase,
    val getMaxHuntWarnFlashTimeUseCase: GetMaxHuntWarnFlashTimeUseCase,
) {

    // Ghost
    private val ghostRepository: GhostRepository by lazy {
        val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource()
        GhostRepositoryImpl(
            ghostLocalDataSource = ghostLocalDataSource
        )
    }
    internal val getGhostUseCase = GetGhostUseCase(
        repository = ghostRepository
    )
    internal val fetchGhostTypesUseCase = FetchGhostTypesUseCase(
        repository = ghostRepository
    )
    internal val getGhostTypeByIdUseCase = GetGhostTypeByIdUseCase(
        repository = ghostRepository
    )

    // Evidence
    private val evidenceRepository: EvidenceRepository by lazy {
        val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource()
        EvidenceRepositoryImpl(
            evidenceLocalDataSource = evidenceLocalDataSource
        )
    }
    internal val getEvidenceUseCase = GetEvidenceUseCase(
        repository = evidenceRepository
    )
    internal val fetchEvidenceTypesUseCase = FetchEvidenceTypesUseCase(
        repository = evidenceRepository
    )
    internal val getEvidenceTypeByIdUseCase = GetEvidenceTypeByIdUseCase(
        repository = evidenceRepository
    )

    // Journal
    internal val fetchGhostEvidencesUseCase = FetchGhostEvidencesUseCase(
        ghostRepository = ghostRepository,
        evidenceRepository = evidenceRepository
    )
    internal val initRuledEvidenceUseCase = InitRuledEvidenceUseCase(
        fetchEvidencesUseCase = fetchEvidenceTypesUseCase
    )

    // Difficulty
    private val difficultyRepository: DifficultyRepository by lazy {
        val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(
            applicationContext = applicationContext
        )
        DifficultyRepositoryImpl(
            localSource = difficultyLocalDataSource
        )
    }
    internal val fetchDifficultiesUseCase = FetchDifficultiesUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyTypeUseCase = GetDifficultyTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyNameUseCase = GetDifficultyNameUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyModifierUseCase = GetDifficultyModifierUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyTimeUseCase = GetDifficultyTimeUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyResponseTypeUseCase = GetDifficultyResponseTypeUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val getDifficultyInitialSanityUseCase = GetDifficultyInitialSanityUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val incrementDifficultyIndexUseCase = IncrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
    )
    internal val decrementDifficultyIndexUseCase = DecrementDifficultyIndexUseCase(
        difficultyRepository = difficultyRepository
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
    internal val fetchMapModifiersUseCase = FetchMapModifiersUseCase(
        repository = mapModifiersRepository
    )

    // Simple Map
    private val simpleMapRepository: SimpleMapRepository by lazy {
        val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
            applicationContext = applicationContext
        )
        SimpleMapRepositoryImpl(
            localSource = simpleMapLocalDataSource
        )
    }
    internal val fetchSimpleMapsUseCase = FetchSimpleMapsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val fetchMapThumbnailsUseCase = FetchMapThumbnailsUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val incrementMapIndexUseCase = IncrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val decrementMapIndexUseCase = DecrementMapIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val incrementMapFloorIndexUseCase = IncrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val decrementMapFloorIndexUseCase = DecrementMapFloorIndexUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapIdUseCase = GetSimpleMapIdUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapNameUseCase = GetSimpleMapNameUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapSizeUseCase = GetSimpleMapSizeUseCase(
        simpleMapRepository = simpleMapRepository
    )
    internal val getSimpleMapSetupModifierUseCase = GetSimpleMapSetupModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    internal val getSimpleMapNormalModifierUseCase = GetSimpleMapNormalModifierUseCase(
        fetchMapModifiersUseCase = fetchMapModifiersUseCase
    )
    internal val getMapModifierUseCase = GetMapModifierUseCase(
        getSimpleMapNormalModifierUseCase = getSimpleMapNormalModifierUseCase,
        getSimpleMapSetupModifierUseCase = getSimpleMapSetupModifierUseCase
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
    internal val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
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

    internal val fetchCodexAchievementsUseCase = FetchAchievementTypesUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexEquipmentUseCase = FetchEquipmentTypesUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexPossessionsUseCase = FetchPossessionTypesUseCase(
        codexRepository = codexRepository
    )

    internal val getEquipmentTypeByEvidenceTypeUseCase = GetEquipmentTypeByEvidenceTypeUseCase()

}
