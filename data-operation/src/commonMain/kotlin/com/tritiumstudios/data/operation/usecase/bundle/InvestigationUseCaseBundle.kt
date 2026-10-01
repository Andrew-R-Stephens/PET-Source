package com.tritiumstudios.data.operation.usecase.bundle

import com.tritiumgaming.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.data.mission.usecase.FetchAllMissionsUseCase
import com.tritiumstudios.data.operation.usecase.GetOperationStateUseCase
import com.tritiumstudios.data.operation.usecase.ResetOperationUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationDifficultyUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationEvidenceUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationHuntWarningUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationMapUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationMissionDataUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationOverridesUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationPhaseUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationSanityUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationTemperatureUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationWeatherUseCase

data class InvestigationUseCaseBundle(
    val getOperationStateUseCase: GetOperationStateUseCase,
    val updateOperationMapUseCase: UpdateOperationMapUseCase,
    val updateOperationDifficultyUseCase: UpdateOperationDifficultyUseCase,
    val updateOperationSanityUseCase: UpdateOperationSanityUseCase,
    val updateOperationPhaseUseCase: UpdateOperationPhaseUseCase,
    val updateOperationHuntWarningUseCase: UpdateOperationHuntWarningUseCase,
    val updateOperationEvidenceUseCase: UpdateOperationEvidenceUseCase,
    val updateOperationGhostDetailsUseCase: UpdateOperationGhostDetailsUseCase,
    val updateOperationMissionDataUseCase: UpdateOperationMissionDataUseCase,
    val updateOperationOverridesUseCase: UpdateOperationOverridesUseCase,
    val updateOperationWeatherUseCase: UpdateOperationWeatherUseCase,
    val updateOperationTemperatureUseCase: UpdateOperationTemperatureUseCase,
    val fetchAllMissionsUseCase: FetchAllMissionsUseCase,
    /*val updateGhostStates: UpdateInvestigationGhostStatesUseCase,*/
    val resetOperationUseCase: ResetOperationUseCase,
    val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase
)