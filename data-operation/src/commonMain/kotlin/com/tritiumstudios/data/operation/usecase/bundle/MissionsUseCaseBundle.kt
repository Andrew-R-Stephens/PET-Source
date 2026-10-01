package com.tritiumstudios.data.operation.usecase.bundle

import com.tritiumstudios.data.operation.usecase.GetOperationStateUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumstudios.data.operation.usecase.UpdateOperationMissionDataUseCase

data class MissionsUseCaseBundle(
    val getOperationStateUseCase: GetOperationStateUseCase,
    val updateOperationGhostDetailsUseCase: UpdateOperationGhostDetailsUseCase,
    val updateOperationMissionDataUseCase: UpdateOperationMissionDataUseCase
)
