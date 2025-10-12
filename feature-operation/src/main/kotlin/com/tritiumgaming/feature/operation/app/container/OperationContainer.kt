package com.tritiumgaming.feature.operation.app.container

/*import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tritiumgaming.shared.operation.domain.difficulty.repository.DifficultyRepository
import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository*/
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.GetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexAchievementsUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexEquipmentUseCase
import com.tritiumgaming.shared.operation.domain.codex.usecase.FetchCodexPossessionsUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.DecrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.FetchDifficultiesUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyInitialSanityUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyModifierUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyNameUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyResponseTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTimeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.GetDifficultyTypeUseCase
import com.tritiumgaming.shared.operation.domain.difficulty.usecase.IncrementDifficultyIndexUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFemaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllFirstNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllMaleNamesUseCase
import com.tritiumgaming.shared.operation.domain.ghostname.usecase.FetchAllSurnamesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostEvidencesUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.FetchGhostsUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetEvidenceByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.GetGhostByIdUseCase
import com.tritiumgaming.shared.operation.domain.journal.usecase.InitRuledEvidenceUseCase
import com.tritiumgaming.shared.operation.domain.map.complex.usecase.FetchComplexMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.FetchMapModifiersUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetMapModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapNormalModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.modifier.usecase.GetSimpleMapSetupModifierUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.DecrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchMapThumbnailsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.FetchSimpleMapsUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapIdUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapNameUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.GetSimpleMapSizeUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapFloorIndexUseCase
import com.tritiumgaming.shared.operation.domain.map.simple.usecase.IncrementMapIndexUseCase
import com.tritiumgaming.shared.operation.domain.mission.usecase.FetchAllMissionsUseCase

class OperationContainer(
    val fetchGhostsUseCase: FetchGhostsUseCase,
    val getGhostByIdUseCase: GetGhostByIdUseCase,
    val fetchEvidencesUseCase: FetchEvidencesUseCase,
    val getEvidenceByIdUseCase: GetEvidenceByIdUseCase,
    val fetchGhostEvidencesUseCase: FetchGhostEvidencesUseCase,
    val initRuledEvidenceUseCase: InitRuledEvidenceUseCase,
    val fetchDifficultiesUseCase: FetchDifficultiesUseCase,
    val getDifficultyTypeUseCase: GetDifficultyTypeUseCase,
    val getDifficultyNameUseCase: GetDifficultyNameUseCase,
    val getDifficultyModifierUseCase: GetDifficultyModifierUseCase,
    val getDifficultyTimeUseCase: GetDifficultyTimeUseCase,
    val getDifficultyResponseTypeUseCase: GetDifficultyResponseTypeUseCase,
    val getDifficultyInitialSanityUseCase: GetDifficultyInitialSanityUseCase,
    val incrementDifficultyIndexUseCase: IncrementDifficultyIndexUseCase,
    val decrementDifficultyIndexUseCase: DecrementDifficultyIndexUseCase,
    val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
    val fetchAllFirstNamesUseCase: FetchAllFirstNamesUseCase,
    val fetchAllMaleNamesUseCase: FetchAllMaleNamesUseCase,
    val fetchAllFemaleNamesUseCase: FetchAllFemaleNamesUseCase,
    val fetchAllSurnamesUseCase: FetchAllSurnamesUseCase,
    val fetchMapModifiersUseCase: FetchMapModifiersUseCase,
    val fetchSimpleMapsUseCase: FetchSimpleMapsUseCase,
    val fetchMapThumbnailsUseCase: FetchMapThumbnailsUseCase,
    val incrementMapIndexUseCase: IncrementMapIndexUseCase,
    val decrementMapIndexUseCase: DecrementMapIndexUseCase,
    val incrementMapFloorIndexUseCase: IncrementMapFloorIndexUseCase,
    val decrementMapFloorIndexUseCase: DecrementMapFloorIndexUseCase,
    val getSimpleMapIdUseCase: GetSimpleMapIdUseCase,
    val getSimpleMapNameUseCase: GetSimpleMapNameUseCase,
    val getSimpleMapSizeUseCase: GetSimpleMapSizeUseCase,
    val getSimpleMapSetupModifierUseCase: GetSimpleMapSetupModifierUseCase,
    val getSimpleMapNormalModifierUseCase: GetSimpleMapNormalModifierUseCase,
    val getMapModifierUseCase: GetMapModifierUseCase,
    val fetchComplexMapsUseCase: FetchComplexMapsUseCase,
    val fetchCodexAchievementsUseCase: FetchCodexAchievementsUseCase,
    val fetchCodexEquipmentUseCase: FetchCodexEquipmentUseCase,
    val fetchCodexPossessionsUseCase: FetchCodexPossessionsUseCase,
    val getAllowHuntWarnAudioUseCase: GetAllowHuntWarnAudioUseCase,
    val getEnableGhostReorderUseCase: GetEnableGhostReorderUseCase,
    val getEnableRTLUseCase: GetEnableRTLUseCase,
    val getMaxHuntWarnFlashTimeUseCase: GetMaxHuntWarnFlashTimeUseCase,
)

/*
class OperationContainer(
    applicationContext: Context,
    dataStore: DataStore<Preferences>
) {

    */
/*
     * Investigation Journal
     *//*


    // Ghost
    private val ghostLocalDataSource: GhostDataSource = GhostLocalDataSource(
        applicationContext = applicationContext
    )
    private val ghostRepository: GhostRepository = GhostRepositoryImpl(
        ghostLocalDataSource = ghostLocalDataSource
    )
    internal val fetchGhostsUseCase = FetchGhostsUseCase(
        repository = ghostRepository
    )
    internal val getGhostByIdUseCase = GetGhostByIdUseCase(
        repository = ghostRepository
    )

    // Evidence
    private val evidenceLocalDataSource: EvidenceDataSource = EvidenceLocalDataSource(
        applicationContext = applicationContext
    )
    private val evidenceRepository: EvidenceRepository = EvidenceRepositoryImpl(
        evidenceLocalDataSource = evidenceLocalDataSource
    )
    internal val fetchEvidencesUseCase = FetchEvidencesUseCase(
        repository = evidenceRepository
    )
    internal val getEvidenceByIdUseCase = GetEvidenceByIdUseCase(
        repository = evidenceRepository
    )

    // Journal
    internal val fetchGhostEvidencesUseCase = FetchGhostEvidencesUseCase(
        ghostRepository = ghostRepository,
        evidenceRepository = evidenceRepository
    )
    internal val initRuledEvidenceUseCase = InitRuledEvidenceUseCase(
        fetchEvidencesUseCase = fetchEvidencesUseCase
    )

    // Difficulty
    private val difficultyLocalDataSource: DifficultyDataSource = DifficultyLocalDataSource(
        applicationContext = applicationContext
    )
    private val difficultyRepository: DifficultyRepository = DifficultyRepositoryImpl(
        localSource = difficultyLocalDataSource
    )
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

    // Mission
    private val missionLocalDataSource: MissionDataSource = MissionLocalDataSource(
        applicationContext = applicationContext,
    )
    internal val missionRepository: MissionRepository = MissionRepositoryImpl(
        localSource = missionLocalDataSource
    )
    internal val fetchAllMissionsUseCase = FetchAllMissionsUseCase(
        missionRepository = missionRepository
    )

    // Ghost Name
    private val ghostNameLocalDataSource: GhostNameDataSource = GhostNameLocalDataSource(
        applicationContext = applicationContext
    )
    internal val ghostNameRepository: GhostNameRepository = GhostNameRepositoryImpl(
        localSource = ghostNameLocalDataSource
    )
    internal val fetchAllFirstNamesUseCase = FetchAllFirstNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllMaleNamesUseCase = FetchAllMaleNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllFemaleNamesUseCase = FetchAllFemaleNamesUseCase(
        repository = ghostNameRepository
    )
    internal val fetchAllSurnamesUseCase = FetchAllSurnamesUseCase(
        repository = ghostNameRepository
    )

    // Map Modifiers
    private val mapModifiersLocalDataSource: MapModifiersDataSource = MapModifiersLocalDataSource(
        applicationContext = applicationContext
    )
    private val mapModifiersRepository: MapModifiersRepository = MapModifiersRepositoryImpl(
        localSource = mapModifiersLocalDataSource
    )
    internal val fetchMapModifiersUseCase = FetchMapModifiersUseCase(
        simpleMapRepository = mapModifiersRepository
    )

    // Simple Map
    private val simpleMapLocalDataSource: SimpleMapDataSource = SimpleMapLocalDataSource(
        applicationContext = applicationContext
    )
    private val simpleMapRepository: SimpleMapRepository = SimpleMapRepositoryImpl(
        localSource = simpleMapLocalDataSource
    )
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
    private val complexMapLocalDataSource: ComplexMapDataSource = ComplexMapLocalDataSource(
        applicationContext = applicationContext,
        service = ComplexMapLocalService()
    )
    private val complexMapRepository: ComplexMapRepository = ComplexMapRepositoryImpl(
        localSource = complexMapLocalDataSource
    )
    internal val fetchComplexMapsUseCase = FetchComplexMapsUseCase(
        complexMapRepository = complexMapRepository
    )

     */
/*
      * Codex
      *//*


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
    private val codexRepository: CodexRepository = CodexRepositoryImpl(
        achievementsLocalDataSource = codexAchievementsLocalDataSource,
        equipmentLocalDataSource = codexEquipmentLocalDataSource,
        possessionsLocalDataSource = codexPossessionsLocalDataSource
    )

    internal val fetchCodexAchievementsUseCase = FetchCodexAchievementsUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexEquipmentUseCase = FetchCodexEquipmentUseCase(
        codexRepository = codexRepository
    )
    internal val fetchCodexPossessionsUseCase = FetchCodexPossessionsUseCase(
        codexRepository = codexRepository
    )

}
*/
